import { HttpInterceptor, HttpRequest, HttpHandler, HttpEvent, HttpErrorResponse } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { Observable, catchError, throwError } from "rxjs";
import { SERVER_URL } from "../app.component";
import { AUTHORIZATION } from "../login/shared/login.service";
import { SessionObjectService } from "../login/shared/session-object.service";
import { Message } from "../utils/message.model";

@Injectable()
export class HttpTokenInterceptor implements HttpInterceptor {
    constructor(private sessionObjectService: SessionObjectService,
        private toastr: ToastrService,
        private router: Router) {}
    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let url: string = req.url;
        if (!url.includes(SERVER_URL + "/anon")) {
            const authValue = req.headers.get(AUTHORIZATION);
            if (authValue && authValue.startsWith("Basic")) {
                return next.handle(req).pipe(
                    catchError((error, caught) => {
                        return this.handleError(error);
                    })
                );
            }
    
            const authReq = req.clone({
                headers: req.headers.set(AUTHORIZATION, "Bearer " + this.sessionObjectService.getToken())
            });
            return next.handle(authReq).pipe(
                catchError((error, caught) => {
                    return this.handleError(error);
                })
            )
        }
        if (url.includes(SERVER_URL + "/anon")) {
           return next.handle(req).pipe(
               catchError((error, caught) => {
                   return this.handleError(error);
               })
           );
        }
        return next.handle(req);
    }

    private handleError(err: HttpErrorResponse): Observable<any> {
        let status = err.status;
        if (status === 0 || status === 500) {
            this.toastr.error("Server is down!", Message.ERROR)
            return throwError(err);
        }
        if (status === 401 || status == 403) {
            if (err.error && err.error["message"]) {
                this.toastr.error(err.error["message"], Message.ERROR);
            } else {
                this.toastr.error(err.error, Message.ERROR);
            }
            //this.router.navigate([""]);
            return throwError(err);
        }
        if (status === 400) {
            if (err.error && err.error["message"]) {
                this.toastr.warning(err.error["message"], Message.WARNING);
            } else {
                this.toastr.warning("Something went wrong! Please try again!", Message.ERROR);
            }
            this.router.navigate([""]);
            return throwError(err);
        }
        if (status === 402) {
            if (err.error && err.error["message"]) {
                this.toastr.error(err.error["message"], Message.ERROR);
            } else {
                this.toastr.error("Something went wrong!", Message.ERROR);
            }
        }

        return null;
    }

}