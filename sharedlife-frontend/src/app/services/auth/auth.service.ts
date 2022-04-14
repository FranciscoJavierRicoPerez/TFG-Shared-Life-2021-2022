import { User } from 'src/app/models/user/user';
import { Observable } from 'rxjs';
import { UserLoginDto } from './../../models/user/user-login-dto/user-login-dto';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtDto } from 'src/app/models/jwt/jwt-dto';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  URL = 'http://localhost:8080/auth/';

  constructor(private HttpClient: HttpClient) { }

  public login(UserLoginDto: UserLoginDto): Observable<JwtDto>{
    return this.HttpClient.post<JwtDto>(this.URL+"login", UserLoginDto);
  }

  public register(newUser: User): Observable<any>{
    return this.HttpClient.post<any>(this.URL+"register", newUser);
  }
}
