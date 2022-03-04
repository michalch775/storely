/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import {ApiClient} from "../../api/client/ApiClient";
import {ApiViewEvents} from "../../utilities/ApiViewEvents";
import EventBus from "js-event-bus";
import {UIError} from "../../errors/UiError";
import {ApiViewNames} from "../../utilities/ApiViewNames";
import {ErrorFactory} from "../../errors/ErrorFactory";
import {ChartBar} from "../../api/entities/ChartBar";
import {HomeWidgets} from "../../api/entities/HomeWidgets";


export class HomeContainerViewModel {

    private readonly _apiClient: ApiClient;
    private readonly _eventBus: EventBus;
    private readonly _apiViewEvents: ApiViewEvents;

    public constructor(
        apiClient: ApiClient,
        eventBus: EventBus,
        apiViewEvents: ApiViewEvents,
    ) {
        this._apiClient = apiClient;
        this._eventBus = eventBus;
        this._apiViewEvents = apiViewEvents;
    }



    public get eventBus(): EventBus {
        return this._eventBus;
    }


    public async callApi(
        onSuccess: (widgets:HomeWidgets, rentalChart:ChartBar[], retrievalChart:ChartBar[]) => void,
        onError: (error: UIError) => void): Promise<void> {

        try {
            this._apiViewEvents.onViewLoading(ApiViewNames.Main);
            const rentalChart = await this._apiClient.getRentalChart();
            const retrievalChart = await this._apiClient.getRetrievalChart();
            const widgets = await this._apiClient.getHomeWidgets();
            this._apiViewEvents.onViewLoaded(ApiViewNames.Main);
            onSuccess(widgets, rentalChart, retrievalChart);


        } catch (e) {
            console.log("EEE",e);
            const error = ErrorFactory.fromException(e);
            this._apiViewEvents.onViewLoadFailed(ApiViewNames.Main, error);
            onError(error);
        }
    }
}
