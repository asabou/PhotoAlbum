import { BaseEntity } from "src/app/commons/model/base-entity.model";
import { User } from "./user.model";

export class UserDetails extends BaseEntity {
    firstName: string;
    lastName: string
    user: User;
}