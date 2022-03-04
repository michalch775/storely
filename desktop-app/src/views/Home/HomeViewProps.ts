/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import {HomeWidgets} from "../../api/entities/HomeWidgets";
import {ChartBar} from "../../api/entities/ChartBar";

export interface HomeViewProps{
    widgets: HomeWidgets | null,
    rentalChart: ChartBar[],
    retrievalChart: ChartBar[]
}
