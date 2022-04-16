import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/user/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseURL = "http://localhost:8080/users";

  constructor(private httpClient: HttpClient) { }

  createUser(user: User): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`+"register", user);
  }

  getUserById(id: number): Observable<User>{
    return this.httpClient.get<User>(`${this.baseURL}/${id}`);
  }

  getUserByUsername(username: string): Observable<User>{
    return this.httpClient.get<User>(`${this.baseURL}/username?username=${username}`);
  }


}
