import axios, {AxiosRequestConfig, Method} from 'axios';
import {Authenticator} from "../../auth/Authenticator";
import {ErrorFactory} from "../../errors/ErrorFactory";
import {EventNames} from "../../events/EventNames";
import {NavigateEvent} from "../../events/NavigateEvent";

export class ApiClient {

  private readonly _url: string;
  private readonly _authenticator: Authenticator;

  public constructor(url: string, authenticator:Authenticator) {

    this._authenticator = authenticator;

    this._url = url;
    if (!this._url.endsWith('/')) {
        this._url += '/';
    }

  }


  public async getItemByCode(code:number){
    return await this._request(`item/${code}?byCode=true`, "GET");
  }

  public async getItemById(id:number){
    return await this._request(`item/${id}`, "GET");
  }

  public async getItems(search:string, offset:number){
    console.log("search",search)
    return await this._request(`item?search=${search}&offset=${offset}`, "GET");
  }


  private async _request(
      path:string,
      method:Method,
      body?:any,
  ){

    const url = `${this._url}${path}`;


    let token;
    try {
      token = await this._authenticator.getAccessToken();

      return await this._requestWithToken(url, method, token, body);
    }
    catch(error1){
      if(!this._is403Error(error1)){
        throw ErrorFactory.fromApiError(error1, url);
      }

      token = await this._authenticator.refreshToken();

      try {
        return await this._requestWithToken(url, method, token, body);
      }
      catch(error2){
        throw ErrorFactory.fromApiError(error2, url);
      }

    }
  }

  private async _requestWithToken(
      url:string,
      method:Method,
      token:string,
      body?:any,
  ){



    const axiosOptions: AxiosRequestConfig = {
      url,
      method,
      headers:this._getHeaders(token),
      data: body
    }

    const response = await axios.request(axiosOptions);
    return response.data;

  }

  private _getHeaders(token:string){
    return({
      'Authorization': `${token}`
    })
  }

  private _is403Error(error:any){
    return error.response && error.response.status === 403;
  }

}
