/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import './App.scss';
import {HashRouter as Router, Route, Routes} from "react-router-dom";
import HomeContainer from "./views/Home/HomeContainer";
import NavComponent from "./components/Nav/NavComponent";
import LoginContainer from "./views/Login/LoginContainer";
import Delays from "./views/Delays/Delays";
import ItemsContainer from "./views/Items/ItemsContainer";
import Shortages from "./views/Shortages/Shortages";
import Users from "./views/Users/Users";
import Settings from "./views/Settings/Settings";
import {AppProps} from "./AppProps";
import React, {useEffect, useState} from "react";
import {AppState} from "./AppState";
import {RouteHelper} from "./utilities/RouteHelper";
import {SetErrorEvent} from "./events/SetErrorEvent";
import {EventNames} from "./events/EventNames";


function App(props:AppProps) : JSX.Element {

    const model = props.viewModel;
    const [state, setState] = useState<AppState>({isInitialised:model.isInitialised});

    useEffect(() => {
        startup();
        return () => cleanup();
    }, []);

    async function startup():Promise<void>{
        try{
            await model.initialise();
            setError(null);
            model.eventBus.on(EventNames.LoginRequired, onLoginRequired);

            setState((s)=>{
                return{
                    ...s,
                    isInitialised:true
                };
            });
        }
        catch(e){
            console.log(e);
            setError(e);
        }
    }

    function cleanup(){
        model.eventBus.detach(EventNames.LoginRequired, onLoginRequired);
    }


    function onLoginRequired(): void {
        model.apiViewEvents.clearState();
        location.hash = '/login';
    }

    async function onHome ():Promise<void> {
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

    async function login ():Promise<void> {
        try{


        }
        catch(e){
            setError(e);
        }
    }

    function setError(e:any):void{
        model.eventBus.emit(EventNames.SetError, null, new SetErrorEvent('main',e));
    }



    function renderInitialScreen(){
        return(<div>initial</div>);
    }

    function renderMain() {

        const itemsProps = {
            viewModel:model.getItemsViewModel()
        };

        const navProps = {
            ipcEvents:props.viewModel.ipcEvents,
            configuration:props.viewModel.configuration
        };

        const loginProps = {
            viewModel:model.getLoginViewModel()
        };

        const homeProps = {
            viewModel:model.getHomeViewModel()
        };

        return (
            <Router>
                <NavComponent {...navProps}/>
                <Routes>
                    <Route path="delay" element={<Delays/>}/>
                    <Route path="item" element={<ItemsContainer {...itemsProps}/>}/>
                    <Route path="shortage" element={<Shortages/>}/>
                    <Route path="user" element={<Users/>}/>

                    <Route path="login" element={<LoginContainer {...loginProps}/>}/>

                    <Route path="settings" element={<Settings/>}/>

                    <Route path="/" element={<HomeContainer {...homeProps}/>}/>
                    <Route path="*" element={<HomeContainer {...homeProps}/>}/>
                </Routes>
            </Router>
        );
    }
    if (!state.isInitialised) {
        return renderInitialScreen();
    } else {
        return renderMain();
    }

}

export default App;
