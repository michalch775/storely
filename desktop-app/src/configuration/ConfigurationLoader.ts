import {Configuration} from './configuration';
import Store from "electron-store";


export class ConfigurationLoader {

    public static load(): Configuration {
        const store = new Store();
        return JSON.parse(store.toString());
    }
}