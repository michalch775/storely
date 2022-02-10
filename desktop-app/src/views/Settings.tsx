import styles from "../styles/Settings.module.scss";
import {useState} from 'react';
import {Store} from '../store/store';
//const { ipcRenderer } = window.require('electron');

function Settings() {
  const store = new Store();
//  const [settings, setSettings] = useState(JSON.parse(store.get('settings')));

  return (
      <div className={styles.settingsBox}>
        Settings
      </div>
  );
}

export default Settings;
