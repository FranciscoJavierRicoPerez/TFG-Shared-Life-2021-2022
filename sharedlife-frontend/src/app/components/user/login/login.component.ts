import { User } from 'src/app/models/user/user';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserLoginDto } from './../../../models/user/user-login-dto/user-login-dto';
import { AuthService } from './../../../services/auth/auth.service';
import { TokenService } from './../../../services/token/token.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  isLogged = false;
  isLoginFail = false;
  roles: string[] = [];
  loginUserDto: UserLoginDto;

  myForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
    password: new FormControl('', [Validators.required]),
  });

  constructor(
    private TokenService: TokenService,
    private AuthService: AuthService,
    private Router: Router
  ) {}

  ngOnInit(): void {
    // Setteamos los valores iniciales
    if (this.TokenService.getToken()) {
      this.isLogged = true;
      this.isLoginFail = false;
      this.roles = this.TokenService.getAuthorities();
    }
  }

  onLogin(): void {
    this.loginUserDto = new UserLoginDto(
      this.myForm.value.username,
      this.myForm.value.password
    );
    this.AuthService.login(this.loginUserDto).subscribe(
      (data) => {
        this.isLogged = true;
        this.isLoginFail = false;

        this.TokenService.setToken(data.token);
        this.TokenService.setUserName(data.username);
        this.TokenService.setAuthorities(data.authorities);
        this.Router.navigate(['/inicio']);
      },
      (err) => {
        this.isLogged = false;
        this.isLoginFail = true;
      }
    );
  }
}
