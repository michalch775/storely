import urlparse from "url-parse";
import {IpcEventNames} from "./IpcEventNames";
import {Configuration} from "../../configuration/Configuration";

export class RendererEvents{

    private readonly _api:any;


    public constructor() {
        this._api=(window as any).api;

        this._setupCallbacks();
    }

    public register(): void {

        this._api.receiveIpcMessage(
            IpcEventNames.ON_PRIVATE_URI_SCHEME_NOTIFICATION,
            this._handlePrivateUriSchemeNotification);
    }

    public async loadConfiguration(): Promise<Configuration> {

        return this._sendIpcMessage(IpcEventNames.ON_GET_CONFIGURATION, {});
    }

    private async _sendIpcMessage(eventName: string, requestData: any): Promise<any> {

        const result = await this._api.sendIpcMessage(eventName, requestData);
        if (result.error) {
            throw result.error;
        }

        return result.data;
    }




    private _tryParseUrl(url: string): any {

        try {
            return urlparse(url, true);
        } catch (e) {
            return null;
        }
    }

}