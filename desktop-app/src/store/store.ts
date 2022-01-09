export class Store {

  private _isElectron:boolean=false;
  private readonly ipcRenderer:any;

  public constructor() {
    try{
      this.ipcRenderer=window.require('electron').ipcRenderer;
      this._isElectron=true;
    }
    catch{
      const settings = {
        darkMode:false,
        navWidth:"300"
      };

      const d = new Date();
      d.setTime(d.getTime() + (31*24*60*60*1000));
      let expires = "expires="+ d.toUTCString();

      if(document.cookie=="")
        document.cookie=`settings=${JSON.stringify(settings)};${expires}`;
    }
  }
  public getIsElectron(){
    return this._isElectron;
  }

  public set(name:string, value:string):void{
    if(this._isElectron){
      this.ipcRenderer.sendSync('store-set', {name, value});
    }
    else{
      const d = new Date();
      d.setTime(d.getTime() + (31*24*60*60*1000));
      let expires = "expires="+ d.toUTCString();
      document.cookie = name + "=" + value + ";" + expires + ";path=/";
    }
  }

  public get(name:string|undefined):string|undefined{
    if(this._isElectron){
      return this.ipcRenderer.sendSync('store-get', name);
    }
    else{
      let cname = name + "=";
      let decodedCookie = decodeURIComponent(document.cookie);
      let ca = decodedCookie.split(';');
      for(let i = 0; i <ca.length; i++) {
        let c = ca[i];
        while (c.charAt(0) == ' ') {
          c = c.substring(1);
        }
        if (c.indexOf(cname) == 0) {
          return c.substring(cname.length, c.length);
        }
      }
      return "";
    }
  }


}
