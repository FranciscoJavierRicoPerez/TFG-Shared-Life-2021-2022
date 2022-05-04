import { Invitation } from './../../models/invitation/invitation';
import { HomeCreateDTO } from 'src/app/models/home/home-create-dto/home-create-dto';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Home } from 'src/app/models/home/home';

@Injectable({
  providedIn: 'root'
})
export class HomeService {

  private baseURL = "http://localhost:8080/home";

  constructor(private httpClient: HttpClient) { }

  createHouse(home: HomeCreateDTO): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`, home);
  }

  getHouseByUsername(username: string): Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}/byUsername?username=${username}`);
  }

  getHomeById(id: string): Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}/id/${id}`);
  }

  sendInvitation(invitation: Invitation): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/invitation`, invitation);
  }

  acceptInvitation(invitation: Invitation): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}/invitation/accept`, invitation);
  }

  getAllHomeMembers(idHome: string): Observable<any>{
    return this.httpClient.get<any>(`${this.baseURL}/id/${idHome}/members`);
  }

  leaveHome(idHome: string, username: string): Observable<any>{
    return this.httpClient.delete(`${this.baseURL}/id/${idHome}/leave?username=${username}`);
  }

  deleteHome(idHome: string): Observable<any>{
    return this.httpClient.delete(`${this.baseURL}/id/${idHome}/delete`);
  }
}
