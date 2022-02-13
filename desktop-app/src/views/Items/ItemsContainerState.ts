import {Item} from "../../api/entities/Item";

export interface ItemsContainerState{
    items:Item[];
    hasMore:boolean;
}