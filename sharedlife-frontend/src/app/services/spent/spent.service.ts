import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class SpentService {

  private baseURL = "http://localhost:8080/spents";

  constructor(
    private HttpClient: HttpClient
  ) { }

  createSpent(spent: any, admin: boolean){
    return this.HttpClient.post(`${this.baseURL}/create?admin=${admin}`, spent);
  }

}
