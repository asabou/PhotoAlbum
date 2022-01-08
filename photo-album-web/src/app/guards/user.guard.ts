import { Injectable } from "@angular/core";
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from "@angular/router";
import { ToastrService } from "ngx-toastr";
import { Observable } from "rxjs";
import { LoginService } from "../login/shared/login.service";
import { AbstractGuard } from "./abstract.guard";

@Injectable()
export class UserGuard extends AbstractGuard implements CanActivate {
    
    constructor(private loginService: LoginService, toastr: ToastrService, router: Router) {
        super(toastr, router);
    }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        let hasAccess = this.loginService.hasUserRights();
        if (!hasAccess) {
           this.showWarningAndNavigateBack();
        }
        console.log("RenterGuard");
        return hasAccess;
    }
    
}