import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SERVER_URL } from "src/app/app.component";
import { AbstractService } from "src/app/commons/model/abstract-service.model";
import { UserDetails } from "./user-details.model";

export const USER_URL = SERVER_URL + "/user";

@Injectable()
export class UserService extends AbstractService<UserDetails> {
    constructor(http: HttpClient) { super(http); }

    addUrl: string = USER_URL + "/register";
    updateUrl: string;
    getUrl: string;
    deleteUrl: string;
    getListUrl: string;

    getOne(id: any): Observable<UserDetails> {
        throw new Error("Method not implemented.");
    }

    add(e: UserDetails): Observable<Object> {
        let url = this.addUrl;
        return this.http.post<UserDetails>(url, e);
    }

    update(e: UserDetails): Observable<Object> {
        throw new Error("Method not implemented.");
    }

    delete(id: any): Observable<Object> {
        throw new Error("Method not implemented.");
    }

    getAllFor(id: any): Observable<UserDetails[]> {
        throw new Error("Method not implemented.");
    }

}