/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import axios, {AxiosRequestConfig, Method} from 'axios';
import {Authenticator} from "../../auth/Authenticator";
import {ErrorFactory} from "../../errors/ErrorFactory";
import {ItemSortBy} from "../enums/ItemSortBy";
import {ShortageSort} from "../enums/ShortageSort";

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

    //  SHORTAGE REQUESTS
    public async getShortages(search:string, offset:number, sort:ShortageSort){
        return await this._request(`itemview?search=${search}&offset=${offset}&sort=${ShortageSort[sort]}`, "GET");
    }

    // ITEM REQUESTS
    public async getItemByCode(code:number){
        return await this._request(`item/${code}?byCode=true`, "GET");
    }

    public async getItemById(id:number){
        return await this._request(`item/${id}`, "GET");
    }

    public async getItems(search:string, offset:number, sort:ItemSortBy){
        console.log(`itemview?search=${search}&offset=${offset}&sort=${ItemSortBy[sort]}`);
        return await this._request(`itemview?search=${search}&offset=${offset}&sort=${ItemSortBy[sort]}`, "GET");
    }

    //  HOME REQUESTS 
    public async getRentalChart(){
        return await this._request(`dashboard/chart/rental`, "GET");
    }

    public async getRetrievalChart(){
        console.log('oh daddy');
        return await this._request(`dashboard/chart/retrieval`, "GET");
    }

    public async getHomeWidgets(){
        return await this._request(`dashboard/widgets`, "GET");
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
        };

        const response = await axios.request(axiosOptions);
        return response.data;

    }

    private _getHeaders(token:string){
        return({
            'Authorization': `${token}`
        });
    }

    private _is403Error(error:any){
        return error.response && error.response.status === 403;
    }

}
