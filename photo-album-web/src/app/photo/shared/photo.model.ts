import { BaseEntity } from "src/app/commons/model/base-entity.model";

export class Photo extends BaseEntity {
    albumId: number;
    title: string;
    date: Date;
    image: any; //bytes
}