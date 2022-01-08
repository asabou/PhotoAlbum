import { AbstractService } from "./abstract-service.model";

export abstract class  AbstractComponent<E> {

    constructor(service: AbstractService<E>) { }

    abstract getEntityId(): number;

    abstract getAllData(): void;

    abstract afterDelete(id: string): void;

    abstract getTableId(): string;
}