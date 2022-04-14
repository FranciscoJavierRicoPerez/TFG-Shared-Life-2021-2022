import { UserService } from './../../../services/user/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user/user';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  id!: number;
  user!: User;

  constructor(
    private userService: UserService,
    private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.getUser();
  }

  getUser(){
    this.id = this.route.snapshot.params['id']; // Obtengo de la ruta la informacion del id
    this.user = new User("", "", "", "", "");
    this.userService.getUserById(this.id).subscribe( data => {
      this.user = data;
    });
  }



}
