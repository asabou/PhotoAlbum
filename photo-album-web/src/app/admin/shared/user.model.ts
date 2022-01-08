import { BaseEntity } from "src/app/commons/model/base-entity.model";
import { Role } from "./role.model";

export class User extends BaseEntity {
    username: string;
    roles: Role[];
}