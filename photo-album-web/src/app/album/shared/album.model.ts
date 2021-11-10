import { BaseEntity } from "src/app/commons/model/base-entity.model";

export class Album extends BaseEntity {
    title: string = "";

    constructor(data: any) {
        super();
        Object.assign(this, data);
    }
}