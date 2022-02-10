import {Category} from "./Category";
import {Group} from "./Group";

export interface ItemTemplate {
    id: number;
    name:string;
    model:string;
    description:string;
    isReturnable:boolean;
    timeLimit:number; //hours
    criticalQuantity:number;
    category:Category;
    groups:Array<Group>;
}