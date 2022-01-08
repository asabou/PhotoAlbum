import { Component, OnInit } from '@angular/core';
import { AbstractAddComponent } from 'src/app/commons/model/abstract-add.component';
import { AdminService } from '../shared/admin.service';
import { UserDetails } from '../shared/user-details.model';

@Component({
  selector: 'app-user-add',
  templateUrl: './user-add.component.html',
  styleUrls: ['./user-add.component.css']
})
export class UserAddComponent extends AbstractAddComponent<UserDetails> implements OnInit {
  constructor(adminService: AdminService) { super(adminService); }
  
  add(e: UserDetails): void {
    throw new Error('Method not implemented.');
  }

  ngOnInit(): void {
  }

}
