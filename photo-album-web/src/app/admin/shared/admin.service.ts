import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SERVER_URL } from "src/app/app.component";
import { AbstractService } from "src/app/commons/model/abstract-service.model";
import { UserDetails } from "./user-details.model";

export const ADMIN_URL = SERVER_URL + "/admin";

@Injectable()
export class AdminService extends AbstractService<UserDetails> {
    constructor(http: HttpClient) {
        super(http);
    }
    
    addUrl: string;
    updateUrl: string;
    getUrl: string;
    deleteUrl: string = ADMIN_URL + "/delete-account/";
    getListUrl: string;

    getOne(id: any): Observable<UserDetails> {
        throw new Error("Method not implemented.");
    }

    add(e: UserDetails): Observable<Object> {
        throw new Error("Method not implemented.");
    }

    update(e: UserDetails): Observable<Object> {
        throw new Error("Method not implemented.");
    }

    delete(id: any): Observable<Object> {
        let url = this.deleteUrl + id;
        return this.http.delete(url);
    }

    getAllFor(id: any): Observable<UserDetails[]> {
        throw new Error("Method not implemented.");
    }

    createAdmin(userDetails: UserDetails): Observable<Object> {
        let url = ADMIN_URL + "/create-admin";
        return this.http.post<UserDetails>(url, userDetails);
    }

    createUser(userDetails: UserDetails): Observable<Object> {
        let url = ADMIN_URL + "/create-user";
        return this.http.post<UserDetails>(url, userDetails);
    }
}