/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */


import {Rental} from "../../api/entities/Rental";
import {ItemView} from "../../api/entities/ItemView";

export interface NonReturnableItemViewProps {
    item: ItemView | null;
    rentals: Rental[];
    onReload: any;
    onDelete: any;
    hasMore: boolean;
}
