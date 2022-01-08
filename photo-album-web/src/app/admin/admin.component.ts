import { Component, OnInit } from '@angular/core';
import { AbstractComponent } from '../commons/model/abstract.component';
import { AdminService } from './shared/admin.service';
import { UserDetails } from './shared/user-details.model';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent extends AbstractComponent<UserDetails> implements OnInit {
  constructor(adminService: AdminService) {
    super(adminService);
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
