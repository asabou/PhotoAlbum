import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, tap } from "rxjs";
import { Role } from "src/app/admin/shared/role.model";
import { SERVER_URL } from "src/app/app.component";
import { ServicesUtils } from "src/app/utils/services-utils.model";
import { AppUser } from "./app-user.model";
import { SessionObject } from "./session-object.model";
import { SessionObjectService } from "./session-object.service";

export const LOGIN_URL = SERVER_URL + "/login";

export const AUTHORIZATION = "Authorization";

@Injectable()
export class LoginService {
    constructor(private http: HttpClient, private sessionObjectService: SessionObjectService) {}

    login(appUser: AppUser): Observable<any> {
        this.sessionObjectService.createSessionObject();

        let credentials = appUser.username;
        credentials += ":";
        credentials += appUser.password;

        let httpHeaders = new HttpHeaders();
        httpHeaders = httpHeaders.set(AUTHORIZATION, "Basic " + btoa(credentials));
        
        return this.http.post<any>(LOGIN_URL, null, {
            headers: httpHeaders,
            observe: 'response'
        })
        .pipe(
            tap((data: any) => {
                this.updateUserAuth(data.headers);
            })
        );
    }

    logout() {
        this.sessionObjectService.destroySessionObject();
    }

    updateUserAuth(headers: HttpHeaders) {
        if (!ServicesUtils.isNullOrUndefinedOrEmpty(headers.get(AUTHORIZATION))) {
            const token = headers.get(AUTHORIZATION).split(" ")[1];
            this.sessionObjectService.setToken(token);
            this.sessionObjectService.setTableColumns();
        }
    }

    private decodeToken(token: string) {
        return ServicesUtils.jwtDecode(token);
    }

    getUsernameFromToken(): string {
        if (!this.isTokenExpired()) {
            let token = this.sessionObjectService.getToken();
            let decodedToken = this.decodeToken(token);
            return decodedToken["sub"].toString();
        }
        return "";
    }

    getUserIdFromToken() {
        if (!this.isTokenExpired()) {
            let token = this.sessionObjectService.getToken();
            let tokenDecoded = this.decodeToken(token);
            return tokenDecoded["userId"].toString();
        } 
        return "";
    }

    isRoleInRoles(rol: string) {
        if (this.isTokenExpired()) {
            return false;
        }
        else {
            let sessionObject: SessionObject = this.sessionObjectService.getSessionObject();
            let token = sessionObject.token;
            let tokenDecoded = this.decodeToken(token);
            let roles: Role[] = tokenDecoded["roles"];
            for (let role of roles) {
                if (role.role === rol) {
                    return true;
                }
            }
        }
        return false;
    }

    hasAdminRights() {
        return this.isRoleInRoles("ADMIN");
    }

    hasUserRights() {
        return this.isRoleInRoles("USER");
    }

    isTokenExpired() {
        let sessionObject = this.sessionObjectService.getSessionObject();
        if (this.isNullOrUndefinedTokenFromSessionObject(sessionObject)) {
            return true;
        } else {
            let token = sessionObject.token;
            token = this.decodeToken(token);
            let date = token["exp"];
            return date.valueOf() > new Date().valueOf();
        }
    }

    private isNullOrUndefinedTokenFromSessionObject(sessionObject: SessionObject) {
        return ServicesUtils.isNullOrUndefined(sessionObject) || ServicesUtils.isNullOrUndefined(sessionObject.token);
    }
}