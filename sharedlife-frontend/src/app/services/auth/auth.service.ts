import { User } from 'src/app/models/user/user';
import { Observable } from 'rxjs';
import { UserLoginDto } from './../../models/user/user-login-dto/user-login-dto';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtDto } from 'src/app/models/jwt/jwt-dto';
import { NewUserDTO } from 'src/app/models/user/new-user-dto/NewUserDTO';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  URL = 'http://localhost:8080/auth/';

  constructor(private HttpClient: HttpClient) { }

  public login(UserLoginDto: UserLoginDto): Observable<JwtDto>{
    return this.HttpClient.post<JwtDto>(this.URL+"login", UserLoginDto);
  }

  public async register(newUser: NewUserDTO): Promise<Observable<any>>{
    return await this.HttpClient.post<any>(this.URL+"register", newUser);
  }
}
