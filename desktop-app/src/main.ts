import {SettingsConfiguration} from "./configuration/SettingsConfiguration";
import {Configuration} from "electron-builder";
import {ApiConfiguration} from "./configuration/ApiConfiguration";
import {MainEvents} from "./plumbing/events/MainEvents";
import {ConfigurationLoader} from "./configuration/ConfigurationLoader";
import {Menu, session, shell} from "electron";
import DefaultMenu from "electron-default-menu";

const { app, BrowserWindow, ipcMain } = require('electron');

const path = require('path');
const isDev = require('electron-is-dev');
const Store = require('electron-store');

class Main{
    private _window: BrowserWindow | null;
    private _ipcEvents: MainEvents;
    private _configuration : Configuration | null;

    public constructor(){
        this._window = null;
        this._ipcEvents = new MainEvents();
        this._configuration =  null;
        //TODO this._setupCallback;
    }

    public execute(): void {

        const primaryInstance = app.requestSingleInstanceLock();
        if (!primaryInstance) {
            app.quit();
            return;
        }

        console.info('STARTING ELECTRON MAIN PROCESS');

        app.on('second-instance', this._onSecondInstance);

        // Initialise the primary instance of the application
        this._initializeApplication();
    }

    private _initializeApplication(): void {

        this._configuration = ConfigurationLoader.load();
        this._ipcEvents.configuration = this._configuration;


        app.on('ready', this._onReady);

        app.on('window-all-closed', this._onAllWindowsClosed);

        app.on('activate', this._onActivate);

        app.allowRendererProcessReuse = true;

        app.on('open-url', this._onOpenUrl);

        const startupUrl = this._getDeepLinkUrl(process.argv);
        if (startupUrl) {
            this._ipcEvents.deepLinkStartupUrl = startupUrl;
        }
    }

    private _onReady(): void {

        // Create the window and use Electron recommended security options
        // https://www.electronjs.org/docs/tutorial/security
        this._window = new BrowserWindow({
            width: 1200,
            height: 800,
            minWidth: 600,
            minHeight: 500,
            webPreferences: {
                nodeIntegration: false,
                enableRemoteModule: false,
                contextIsolation: true,
                worldSafeExecuteJavaScript: true,
                preload: path.join(app.getAppPath(), './electronUtils.js'),
            },
        });

        this._ipcEvents.window = this._window;

        this._registerPrivateUriScheme();

        const menu = DefaultMenu(app, shell);
        Menu.setApplicationMenu(Menu.buildFromTemplate(menu));

        this._window.loadURL(
            isDev
                ? 'http://localhost:3000'
                : `file://${path.join(__dirname, '../build/index.html')}`
        )

        //this._window.loadFile('./index.html');

        // Configure HTTP headers
        this._initialiseOutgoingHttpRequestHeaders();

        this._window.on('closed', this._onClosed);

        this._ipcEvents.register();

        // this._window.webContents.openDevTools();
    }

    private _onActivate(): void {

        if (this._window === null) {
            this._onReady();
        }
        //if (BrowserWindow.getAllWindows().length === 0) createWindow();
    }

    private _onSecondInstance(event: any, argv: any) {

        const url = this._getDeepLinkUrl(argv);
        if (url) {
            this._receiveNotificationInRunningInstance(url);
        }
    }

    private _initialiseOutgoingHttpRequestHeaders() {

        const headerCallback = (details: any, callback: any) => {

            if (details.requestHeaders.Origin) {
                delete details.requestHeaders.Origin;
            }

            callback({cancel: false, requestHeaders: details.requestHeaders});
        };
        session.defaultSession.webRequest.onBeforeSendHeaders({urls: []} as any, headerCallback);
    }

    private _onOpenUrl(event: any, schemeData: string) {

        event.preventDefault();

        if (this._window) {

            // If we have a running window we can just forward the notification to it
            this._receiveNotificationInRunningInstance(schemeData);
        } else {

            // If this is a startup deep linking message we need to store it until after startup
            this._ipcEvents.deepLinkStartupUrl = schemeData;
        }
    }

    private _receiveNotificationInRunningInstance(privateSchemeUrl: string) {

        // The existing instance of the app brings itself to the foreground
        if (this._window) {

            if (this._window.isMinimized()) {
                this._window.restore();
            }

            this._window.focus();
        }

        // Send the event to the renderer side of the app
        this._ipcEvents.sendPrivateSchemeNotificationUrl(privateSchemeUrl);
    }

    private _getDeepLinkUrl(argv: any): string | null {

        for (const arg of argv) {
            const value = arg as string;
            if (value.indexOf(this._configuration!.oauth.privateSchemeName) !== -1) {
                return value;
            }
        }

        return null;
    }






    const settingsConfiguration:SettingsConfiguration={
        darkMode:false,
        navWidth:300
    };
    const apiConfiguration:ApiConfiguration={
        url:"http://localhost:3000/",
        token:null
    };


    const defaults:Configuration = {
        api:apiConfiguration,
        settings:settingsConfiguration
    } as Configuration;



    const store = new Store({defaults});

    ipcMain.on('store-set', (event:any, {name, value}:any) => {
    store.set(name, value);
    event.returnValue=0;
});

ipcMain.on('store-get', (event:any, name:any) => {
    //event.returnValue = 1;
    event.returnValue = store.get(name, undefined);
});

//require('@electron/remote/main').initialize()

function createWindow() {
    // Create the browser window.
    const win = new BrowserWindow({
        width: 800,
        height: 600,
        minWidth:600,
        minHeight:400,
        webPreferences: {
            nodeIntegration: false,
            enableRemoteModule: false,
            contextIsolation:true,
            worldSafeExecuteJavaScript:true,
            preload:path.join(app.getAppPath(), "./electronUtils.js")
        }
    })

    win.loadURL(
        isDev
            ? 'http://localhost:3000'
            : `file://${path.join(__dirname, '../build/index.html')}`
    )
}

app.on('ready', createWindow)

// Quit when all windows are closed.
app.on('window-all-closed', function () {
    // On OS X it is common for applications and their menu bar
    // to stay active until the user quits explicitly with Cmd + Q
    if (process.platform !== 'darwin') {
        app.quit();
    }
})

app.on('activate', function () {
    // On OS X it's common to re-create a window in the app when the
    // dock icon is clicked and there are no other windows open.
    if (BrowserWindow.getAllWindows().length === 0) createWindow();
})

app.on('open-url', function(event:any, schemeData:string){
    event.preventDefault();

    if (win) {
        this._receiveNotificationInRunningInstance(schemeData);
    } else {
        this._ipcEvents.deepLinkStartupUrl = schemeData;
    }
})

}
}

