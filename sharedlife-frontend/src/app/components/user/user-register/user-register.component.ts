import { Router } from '@angular/router';
import { AuthService } from './../../../services/auth/auth.service';
import { UserService } from './../../../services/user/user.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user/user';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { ThisReceiver } from '@angular/compiler';
import { NewUserDTO } from 'src/app/models/user/new-user-dto/NewUserDTO';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.css']
})
export class UserRegisterComponent implements OnInit {

  user: NewUserDTO;

  myForm = new FormGroup({
    firstName: new FormControl('', Validators.required),
    lastName: new FormControl('', Validators.required),
    email: new FormControl('', [Validators.required, Validators.email]),
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
    isOwner: new FormControl(false) // Inicialización del checkbox a false (USUARIO)
  });

  constructor(
    private AuthService: AuthService,
    private Router: Router
  ) {}

  ngOnInit(): void {
  }

  saveUser(): void{
    console.log(this.myForm);
    this.user = new NewUserDTO(
      this.myForm.value.firstName,
      this.myForm.value.lastName,
      this.myForm.value.email,
      this.myForm.value.password,
      this.myForm.value.username);
    if(this.myForm.value.isOwner == true){
      this.user.roles.push("admin");
    }
    console.log(this.user);
    this.AuthService.register(this.user).subscribe();
    this.Router.navigate(['/login'])
  }

  onSubmit(): void{
    this.saveUser();
  }
}
