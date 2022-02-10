import './App.scss';
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./views/Home";
import Nav from "./components/Nav";
import Login from "./views/Login";
import Delays from "./views/Delays";
import Items from "./views/Items";
import Shortages from "./views/Shortages";
import Users from "./views/Users";
import Settings from "./views/Settings";
import {AppProps} from "./AppProps";
import {useState, useEffect} from "react";
import {AppState} from "./AppState";
import {RouteHelper} from "./plumbing/utilities/RouteHelper";
import {SetErrorEvent} from "./plumbing/events/SetErrorEvent";
import {EventNames} from "./plumbing/events/EventNames";


function App(props:AppProps) : JSX.Element {

  const model = props.viewModel;
  const [state, setState] = useState<AppState>({isInitialised:model.isInitialised});

  useEffect(() => {
    startup();
    return () => cleanup();
  }, []);

  const startup = async ():Promise<void> =>{
    try{
      await model.initialise();
      setError(null);
      model.eventBus.on(EventNames.LoginRequired, onLoginRequired);

      setState((s)=>{
        return{
          ...s,
          isInitialised:true
        };
      })
    }
    catch(e){
      setError(e);
    }
  }

  const cleanup = async ():Promise<void> =>{
    model.eventBus.detach(EventNames.LoginRequired, onLoginRequired);
  }

  const onLoginRequired = async ():Promise<void> =>{
    // model.apiViewEvents.clearState();
    // LoginNavigation.navigateToLoginRequired();
  }

  const onHome = async ():Promise<void> =>{
    if(!state.isInitialised){
      cleanup();
      await startup();
    }

    if(state.isInitialised){
      if(RouteHelper.isInLoginRequiredView()){
        //const isLoggedIn = await model.authenticator.isLoggedIn();
        //await login();
        //return()
      }

      if(RouteHelper.isInHomeView()){
        model.reloadMainView();
      }
      else{
        window.location.hash = "#";
      }

    }
  }

  const login = async ():Promise<void> =>{
    try{

    }
    catch(e){
      setError();
    }
  }

  function setError(e:any):void{
    model.eventBus.emit(EventNames.SetError, null, new SetErrorEvent('main',e));
  }

  return (
    <Router>
    <Nav/>
      <Routes>
        <Route path="delay" element={<Delays/>} />
        <Route path="item" element={<Items/>} />
        <Route path="shortage" element={<Shortages/>} />
        <Route path="user" element={<Users/>} />

        <Route path="login" element={<Login/>} />

        <Route path="settings" element={<Settings/>} />

        <Route path="/" element={<Home/>} />
      </Routes>
    </Router>
  );
}

export default App;
