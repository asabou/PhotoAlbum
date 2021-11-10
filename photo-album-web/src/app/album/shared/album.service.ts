import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { map, Observable, pipe, tap } from "rxjs";
import { SERVER_URL } from "src/app/app.component";
import { AbstractService } from "src/app/commons/model/abstract-service.model";
import { Album } from "./album.model";

export const ALBUM_URL = SERVER_URL + "/album";

@Injectable()
export class AlbumService extends AbstractService<Album> {
    constructor(http: HttpClient) {
        super(http);
    }

    addUrl: string = ALBUM_URL + "/create";
    updateUrl: string = ALBUM_URL + "/update";
    getUrl: string = ALBUM_URL + "";
    deleteUrl: string = ALBUM_URL + "/delete";
    getListUrl: string = ALBUM_URL + "/all";

    getOne(id: any): Observable<Album> {
        throw new Error("Method not implemented.");
    }

    add(e: Album): Observable<Object> {
        return this.http.post<Album>(this.addUrl, e);
    }

    update(e: Album): Observable<Object> {
        return this.http.put<Album>(this.updateUrl, e);
    }

    delete(id: any): Observable<Object> {
        let url = this.deleteUrl + "/" + id;
        return this.http.delete(url);
    }

    getAllFor(id: any): Observable<Album[]> {
        return this.http.get<Album[]>(this.getListUrl).pipe(
            tap(res => this.store(res)),
            map(res => res.map((item: Album) => {
                return item;
            })
        ));
    }
    
}