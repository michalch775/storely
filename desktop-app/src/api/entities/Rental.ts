import {Runtime} from "inspector";
import {User} from "./User";

export interface Rental {
    id:number;
    rentDate:Date;
    returnDate:Date|null;
    isReturnable:boolean;
    quantity:number;
    user:User;

}