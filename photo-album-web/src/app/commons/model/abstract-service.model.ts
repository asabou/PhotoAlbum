import { Injectable } from "@angular/core";
import { BaseEntity } from "./base-entity.model";
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from "rxjs";

@Injectable()
export abstract class AbstractService<
    E extends BaseEntity
    > {
    constructor(protected http: HttpClient) {
    }
    
    private jsonResponse = new Subject<any[]>();
    jsonResponse$ = this.jsonResponse.asObservable();

    abstract addUrl: string;
    abstract updateUrl: string;
    abstract getUrl: string;
    abstract deleteUrl: string;
    abstract getListUrl: string;
    
    store(data: any[]) {
        this.jsonResponse.next(data);
    }

    abstract getOne(id: any): Observable<E>;
    abstract add(e: E): Observable<Object>;
    abstract update(e: E): Observable<Object>;
    abstract delete(id: any): Observable<Object>;
    abstract getAllFor(id: any): Observable<E[]>;
}