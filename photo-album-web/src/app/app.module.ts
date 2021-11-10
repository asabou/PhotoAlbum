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
    AbstractTableComponent
  ],
  imports: [
    BrowserModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
