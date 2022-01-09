export class ApiClient {

  private readonly _url: string;
  private  _token: string;


  public constructor(url: string, token: string) {

    this._url = url;
    if (!this._url.endsWith('/')) {
        this._url += '/';
    }
    this._token=token;

}


}
