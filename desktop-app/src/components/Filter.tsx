import styles from "../styles/Filter.module.scss";
import React, {useState} from 'react';
import {ReactComponent as ArrowDown} from '../assets/iosArrowDown.svg';
import {ReactComponent as ArrowUp} from '../assets/iosArrowUp.svg';


function Filter() {

  const [filterOpen, setFilterOpen] = useState(false);

  const onClick = function(){
    setFilterOpen(!filterOpen);

  }

  return (
    <div className={styles.main} onClick={()=>onClick()}>
      <div className={styles.text}>Filtruj</div>
      <div className={styles.arrow}>
        {filterOpen?<ArrowUp/>:<ArrowDown/>}
      </div>
      {filterOpen&&
      <div className = {styles.filterOptions}>

      </div>
      }
    </div>
  );

}

export default Filter;
