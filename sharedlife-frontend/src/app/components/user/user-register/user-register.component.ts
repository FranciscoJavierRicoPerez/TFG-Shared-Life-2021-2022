import { UserService } from './../../../services/user/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user/user';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent implements OnInit {

  user: User = new User();

  myForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required])
  });

  constructor(
    private UserService: UserService) {}

  ngOnInit(): void {
  }

  saveUser(){
    this.UserService.createUser(this.myForm.value).subscribe( data => {
      console.log(data);
      // HACER UNA REDIRECCIÓN A LA PAGINA DE LOGIN CUANDO ESTE HECHA
    }, error => console.log(error));
  }

  onSubmit(){
    console.warn(this.myForm.value);
    this.saveUser();
  }
}
