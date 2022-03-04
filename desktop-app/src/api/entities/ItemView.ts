import {Group} from "./Group";
import {Category} from "./Category";

export interface ItemView {
    id: number;
    quantity:number;
    model:string;
    name:string;
    timeLimit:number;
    criticalQuantity:number;
    groups:Group[];
    category:Category;
    returnable:boolean;
    added:Date|null;
}
