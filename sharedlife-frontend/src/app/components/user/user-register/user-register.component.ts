import { UserService } from './../../../services/user/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user/user';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent implements OnInit {

  user: User = new User();

  constructor(private UserService: UserService) { }

  ngOnInit(): void {
  }

  saveUser(){
    this.UserService.createUser(this.user).subscribe( data => {
      console.log(data);
      // HACER UNA REDIRECCIÓN A LA PAGINA DE LOGIN CUANDO ESTE HECHA
    }, error => console.log(error));
  }

  onSubmit(){
    console.log(this.user);
    this.saveUser();
  }

}
