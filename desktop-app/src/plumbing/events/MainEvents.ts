import {Configuration} from "../../configuration/Configuration";
import {BrowserWindow, ipcMain} from "electron";
import {IpcEventNames} from "../ipc/IpcEventNames";
import Opener from "opener";

export class MainEvents{
    private _configuration : Configuration|null;
    private _window : BrowserWindow|null;
    private _deepLinkStartupUrl :string | null;

    public constructor() {
        this._configuration = null;
        this._window = null;
        this._deepLinkStartupUrl = null;
        this._setupCallbacks();
    }

    public set configuration(configuration: Configuration) {
        this._configuration = configuration;
    }

    public set window(window: BrowserWindow) {
        this._window = window;
    }

    public set deepLinkStartupUrl(startupUrl: string) {
        this._deepLinkStartupUrl = startupUrl;
    }

    public register(): void {
        ipcMain.on(IpcEventNames.ON_GET_CONFIGURATION, this._getConfiguration);
        ipcMain.on(IpcEventNames.ON_GET_DEEP_LINK_STARTUP_URL, this._getDeepLinkStartupUrl);
        ipcMain.on(IpcEventNames.ON_OPEN_SYSTEM_BROWSER, this._openSystemBrowser);
    }

    public sendPrivateSchemeNotificationUrl(url: string): void {
        this._window!.webContents.send(IpcEventNames.ON_PRIVATE_URI_SCHEME_NOTIFICATION, url);
    }

    private async _getConfiguration(): Promise<void> {
        this._sendResponse(IpcEventNames.ON_GET_CONFIGURATION, this._configuration, null);
    }

    private _getDeepLinkStartupUrl(): void {
        this._sendResponse(IpcEventNames.ON_GET_DEEP_LINK_STARTUP_URL, this._deepLinkStartupUrl, null);
    }

    private _openSystemBrowser(...args: any[]): void {

        try {

            Opener(args[1]);
            this._sendResponse(IpcEventNames.ON_OPEN_SYSTEM_BROWSER, null, null);

        } catch (e) {

            this._sendResponse(IpcEventNames.ON_OPEN_SYSTEM_BROWSER, null, e);
        }
    }

    private _sendResponse(eventName: string, data: any, error: any) {
        this._window!.webContents.send(eventName, {data, error});
    }

    private _setupCallbacks() {
        this._getConfiguration = this._getConfiguration.bind(this);
        this._getDeepLinkStartupUrl = this._getDeepLinkStartupUrl.bind(this);
        this._openSystemBrowser = this._openSystemBrowser.bind(this);
    }

}