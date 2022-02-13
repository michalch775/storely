import styles from "../../styles/Nav.module.scss";
import { useLocation } from 'react-router-dom';
import React, { useState, useEffect, useRef, useCallback } from "react";
import {Link} from 'react-router-dom';
import {NavComponentProps} from "./NavComponentProps";

function NavComponent(props:NavComponentProps) {


  const location = useLocation();

  const sidebarRef = useRef<any>(null);
  const [isResizing, setIsResizing] = useState(false);
  const [sidebarWidth, setSidebarWidth] = useState(300);

  const startResizing = useCallback((mouseDownEvent) => {
    setIsResizing(true);
  }, []);

  const stopResizing = useCallback(() => {
    setIsResizing(false);
    if(sidebarRef.current!=null && !isNaN(sidebarRef.current.scrollWidth))
      props.ipcEvents.setToStore("settings.navWidth",sidebarRef.current.scrollWidth.toString())
  }, []);

  const resize = useCallback(
    (mouseMoveEvent) => {
      if (isResizing&&sidebarRef.current!=null) {
        setSidebarWidth(
          mouseMoveEvent.clientX -
            sidebarRef.current.getBoundingClientRect().left
        );
      }
    },[isResizing]);

    useEffect(() => {
      window.addEventListener("mousemove", resize);
      window.addEventListener("mouseup", stopResizing);
      return () => {
        window.removeEventListener("mousemove", resize);
        window.removeEventListener("mouseup", stopResizing);
      };
    }, [resize, stopResizing]);

    useEffect(() => {
      if(props.configuration)
        setSidebarWidth(props.configuration.settings.navWidth);
    }, []);

    if(location.pathname==="/login")
      return null;

  return (
    <nav
      ref={sidebarRef}
      className={styles.mainBox}
      style={{ width: sidebarWidth }}
      onMouseDown={(e) => e.preventDefault()}>

      <div className={styles.logo}>MyInventory</div>

      <ul className={styles.list}>
        <li><Link to="/" className={styles.linkBox}>Strona główna</Link></li>
        <li><Link to="/shortage" className={styles.linkBox}>Deficyt</Link></li>
        <li><Link to="/" className={styles.linkBox}>Przekroczone terminy</Link></li>
        <li><Link to="/item" className={styles.linkBox}>Przedmioty</Link></li>
        <li><Link to="user" className={styles.linkBox}>Użytkownicy</Link></li>
        <li><Link to="rental" className={styles.linkBox}>Wydania zwrotne</Link></li>
        <li><Link to="retrieval" className={styles.linkBox}>Wydania bezzwrotne</Link></li>



      </ul>

      <Link to="settings" className={styles.linkBox}>Ustawienia</Link>

      <div className={styles.appSidebarResizer} onMouseDown={startResizing}/>

    </nav>
  );
}

export default NavComponent;
