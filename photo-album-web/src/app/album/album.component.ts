import { Component, OnInit } from '@angular/core';
import { AbstractComponent } from '../commons/model/abstract.component';
import { Album } from './shared/album.model';
import { AlbumService } from './shared/album.service';

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.css']
})
export class AlbumComponent extends AbstractComponent<Album> implements OnInit {
  constructor(albumService: AlbumService) {
    super(albumService);
  }

  getEntityId(): number {
    throw new Error('Method not implemented.');
  }

  getAllData(): void {
    throw new Error('Method not implemented.');
  }

  afterDelete(id: string): void {
    throw new Error('Method not implemented.');
  }
  
  getTableId(): string {
    throw new Error('Method not implemented.');
  }

  ngOnInit(): void {
  }

}
