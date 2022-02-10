import {ApiClient} from "./api/client/ApiClient";
import EventBus from "js-event-bus";
import {EventNames} from "./plumbing/events/EventNames";
import {ReloadMainViewEvent} from "./plumbing/events/ReloadMainViewEvent";
import {Configuration} from "./configuration/Configuration";

export class AppViewModel{
    private _apiClient : ApiClient | null;
    private _isInitialised : boolean;
    private _eventBus:EventBus;
    private _configuration:Configuration|null;
    private _ipcEvents:RendererEvents;

    public constructor() {
        this._apiClient = null;
        this._isInitialised = false;
        this._eventBus = new EventBus();
        this._configuration=null;
        this
    }

    public async initialise():Promise<void>{
        this._configuration = await this._ipcEvents.loadConfiguration();

        this._apiClient = new ApiClient(this._configuration.app.apiBaseUrl, this._authenticator);
    }

    public get isInitialised (): boolean{
        return this._isInitialised;
    }

    public get eventBus():EventBus{
        return this._eventBus;
    }

    public reloadMainView(): void {
        this._eventBus.emit(EventNames.ReloadMainView, null, new ReloadMainViewEvent());
    }




}