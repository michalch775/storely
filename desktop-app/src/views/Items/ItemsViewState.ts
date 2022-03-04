/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */

import {ItemSortBy} from "../../api/enums/ItemSortBy";

export interface ItemsViewState{
    offset:number;
    text:string;
    sortBy:ItemSortBy
    isLoading:boolean;
}
