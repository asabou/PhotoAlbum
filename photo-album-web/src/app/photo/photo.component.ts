import { Component, OnInit } from '@angular/core';
import { AbstractComponent } from '../commons/model/abstract.component';
import { Photo } from './shared/photo.model';
import { PhotoService } from './shared/photo.service';

@Component({
  selector: 'app-photo',
  templateUrl: './photo.component.html',
  styleUrls: ['./photo.component.css']
})
export class PhotoComponent extends AbstractComponent<Photo> implements OnInit {
  constructor(photoService: PhotoService) {
    super(photoService);
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
