import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { ServicesUtils } from "src/app/utils/services-utils.model";
import { SessionObject } from "./session-object.model";

export const SESSION_OBJECT = "SESSION_OBJECT";

@Injectable()
export class SessionObjectService {
    constructor(private http: HttpClient) { }

    createSessionObject() {
        if (ServicesUtils.isNullOrUndefined(localStorage.getItem(SESSION_OBJECT))) {
            let sessionObject: SessionObject = new SessionObject();
            this.setSessionObject(sessionObject);
        }
    }

    destroySessionObject() {
        localStorage.removeItem(SESSION_OBJECT);
    }

    private setSessionObject(sessionObject: SessionObject) {
        localStorage.setItem(SESSION_OBJECT, ServicesUtils.jsonStringify(sessionObject));
    }

    getSessionObject(): SessionObject {
        return ServicesUtils.jsonParse(localStorage.getItem(SESSION_OBJECT) || "");
    }

    setToken(token: string) {
        let sessionObject: SessionObject = this.getSessionObject();
        sessionObject.token = token;
        this.setSessionObject(sessionObject);
    }

    getToken(): string {
        let sessionObject: SessionObject = this.getSessionObject();
        return sessionObject.token;
    }

    setTableColumns(): void {
        this.http.get("assets/columns.json", { responseType: "json" }).subscribe(tableColumns => {
            let sessionObject: SessionObject = this.getSessionObject();
            sessionObject.tableColumns = tableColumns;
            this.setSessionObject(sessionObject);
        });
    }

    getTableColumns(): Object {
        let sessionObject: SessionObject = this.getSessionObject();
        return sessionObject.tableColumns;
    }
}