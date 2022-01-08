import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { SERVER_URL } from "src/app/app.component";
import { AbstractService } from "src/app/commons/model/abstract-service.model";
import { Photo } from "./photo.model";

export const PHOTO_URL = SERVER_URL + "/photo";

@Injectable()
export class PhotoService extends AbstractService<Photo> {
    constructor(http: HttpClient) {
        super(http);
    }

    addUrl: string = PHOTO_URL + "/create";
    updateUrl: string = PHOTO_URL + "/update";
    getUrl: string;
    deleteUrl: string = PHOTO_URL + "/delete/";
    getListUrl: string = PHOTO_URL + "/all/";

    getOne(id: any): Observable<Photo> {
        throw new Error("Method not implemented.");
    }

    add(e: Photo): Observable<Object> {
        let url = this.addUrl;
        return this.http.post<Photo>(url, e);
    }

    update(e: Photo): Observable<Object> {
        let url = this.updateUrl;
        return this.http.put<Photo>(url, e);
    }

    delete(id: any): Observable<Object> {
        let url = this.deleteUrl + id;
        return this.http.delete(url);
    }

    deletePhotoFromAlbum(idAlbum: any, idPhoto: any): Observable<Object> {
        let url = PHOTO_URL + "/delete-from-album/" + idAlbum + "/" + idPhoto;
        return this.http.delete(url);
    }

    getAllFor(id: any): Observable<Photo[]> {
        let url = this.getListUrl + id;
        return this.http.get<Photo[]>(url);
    }
}