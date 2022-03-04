/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import {ChartBar} from "../../api/entities/ChartBar";
import {HomeWidgets} from "../../api/entities/HomeWidgets";

export interface HomeContainerState{
    widgets: HomeWidgets | null;
    rentalChart: ChartBar[];
    retrievalChart: ChartBar[];
}
