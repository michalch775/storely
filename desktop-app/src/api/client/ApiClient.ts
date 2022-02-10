import axios, {AxiosRequestConfig, Method} from 'axios';

export class ApiClient {

  private readonly _url: string;

  public constructor(url: string) {

    this._url = url;
    if (!this._url.endsWith('/')) {
        this._url += '/';
    }

  }


  public async getItemByCode(token:string, code:number){
    return await this._request(`/item/${code}?byCode=true`, "GET",token);
  }

  public async getItemById(token:string, id:number){
    return await this._request(`/item/${id}`, "GET",token);
  }

  public async getItems(token:string, search:string, offset:number){
    return await this._request(`/item?search${search}&${offset}`, "GET",token);
  }


  public async login(email:string, password:string){
    const body = {
      "username":email,
      "password":password
    }

    const response  = await this._requestWithoutToken("login", "POST", body);
    return response.token;
  }

  private async _request(
      path:string,
      method:Method,
      token:string,
      body?:any,

  ){
    try {
      const url = `${this._url}${path}`;

      const axiosOptions: AxiosRequestConfig = {
        url,
        method,
        data: body,
        headers: this._getHeaders(token)
      }

      const response = await axios.request(axiosOptions);
      return response.data;
    }
    catch(error){
      throw new Error("api_request_error");
    }
  }

  private async _requestWithoutToken(
      path:string,
      method:Method,
      body?:any,

  ){
    try {
      const url = `${this._url}${path}`;

      const axiosOptions: AxiosRequestConfig = {
        url,
        method,
        data: body
      }

      const response = await axios.request(axiosOptions);
      return response.data;
    }
    catch(error){
      throw new Error("api_request_error");
    }
  }

  private _getHeaders(token:string){
    return({
      'Authorization': `${token}`
    })
  }

  private _403handler(){
    throw new Error ("403_error");
  }

}
