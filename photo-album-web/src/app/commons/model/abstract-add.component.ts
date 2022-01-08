import { AbstractService } from "./abstract-service.model";

export abstract class AbstractAddComponent<E> {
    constructor(service: AbstractService<E>) {}

    abstract add(e: E): void;
}