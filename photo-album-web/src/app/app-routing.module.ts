import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AdminComponent } from "./admin/admin.component";
import { AlbumComponent } from "./album/album.component";
import { AdminGuard } from "./guards/admin.guard";
import { UserGuard } from "./guards/user.guard";
import { LoginComponent } from "./login/login.component";

const routes: Routes = [
    { 
        path: 'login', 
        component: LoginComponent 
    },
    {
        path: 'admin',
        component: AdminComponent,
        canActivate: [AdminGuard]
    },
    {
        path: 'album',
        component: AlbumComponent,
        canActivate: [UserGuard]
    }
  ];
  
  @NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
  })
export class AppRoutingModule { }