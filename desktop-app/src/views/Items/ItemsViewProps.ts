import {Item} from "../../api/entities/Item";

export interface ItemsViewProps{
    items:Item[];
    onReload:any;
    hasMore:boolean;
}