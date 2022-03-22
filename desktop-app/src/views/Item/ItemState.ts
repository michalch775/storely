/*
 * All Rights Reserved
 *
 * Copyright (c) 2022 Michał Chruścielski
 */
import {Rental} from "../../api/entities/Rental";
import {ItemView} from "../../api/entities/ItemView";
import {Item} from "../../api/entities/Item";

export interface ItemState {
    item: ItemView | null;
    rentals: Rental[];
    hasMore: boolean;
    entities: Item[] | null;
}
