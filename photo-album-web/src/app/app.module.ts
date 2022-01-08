import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { AlbumComponent } from './album/album.component';
import { PhotoComponent } from './photo/photo.component';
import { AdminComponent } from './admin/admin.component';
import { AlbumAddComponent } from './album/album-add/album-add.component';
import { AlbumDetailsComponent } from './album/album-details/album-details.component';
import { PhotoAddComponent } from './photo/photo-add/photo-add.component';
import { PhotoDetailsComponent } from './photo/photo-details/photo-details.component';
import { UserAddComponent } from './admin/user-add/user-add.component';
import { UserDetailsComponent } from './admin/user-details/user-details.component';
import { AbstractTableComponent } from './commons/abstract-table/abstract-table.component';
import { LoginComponent } from './login/login.component';
import { SessionObjectService } from './login/shared/session-object.service';
import { LoginService } from './login/shared/login.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpTokenInterceptor } from './interceptors/http-token-interceptor';
import { AdminGuard } from './guards/admin.guard';
import { UserGuard } from './guards/user.guard';
import { ImageBytesService } from './utils/image-bytes.service';
import { AdminService } from './admin/shared/admin.service';
import { PhotoService } from './photo/shared/photo.service';
import { AlbumService } from './album/shared/album.service';
import { UserService } from './admin/shared/user.service';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatPaginatorModule } from '@angular/material/paginator';
import { LayoutModule } from '@angular/cdk/layout';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSortModule } from '@angular/material/sort';
import { MatTableModule } from '@angular/material/table';
import { ToastrModule } from 'ngx-toastr';

@NgModule({
  declarations: [
    AppComponent,
    AlbumComponent,
    PhotoComponent,
    AdminComponent,
    AlbumAddComponent,
    AlbumDetailsComponent,
    PhotoAddComponent,
    PhotoDetailsComponent,
    UserAddComponent,
    UserDetailsComponent,
    AbstractTableComponent,
    LoginComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    MatPaginatorModule,
    MatSortModule,
    MatTableModule,
    MatFormFieldModule,
    ReactiveFormsModule,
    LayoutModule,
    ToastrModule.forRoot()
  ],
  providers: [
    SessionObjectService,
    LoginService,
    AdminService,
    AlbumService,
    PhotoService,
    UserService,
    ImageBytesService,
    AdminGuard,
    UserGuard,
    { provide: HTTP_INTERCEPTORS, useClass: HttpTokenInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
