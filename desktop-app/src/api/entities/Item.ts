import {ItemTemplate} from "./ItemTemplate";

export interface Item {
    id: number;
    quantity:number;
    added:Date;
    itemTemplate:ItemTemplate;

}
