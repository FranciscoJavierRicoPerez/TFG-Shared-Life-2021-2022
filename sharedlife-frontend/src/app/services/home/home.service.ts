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

  createHouse(home: Home): Observable<Object>{
    return this.httpClient.post(`${this.baseURL}`, home);
  }
}
