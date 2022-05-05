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

  getAllDebtsByUsername(username: string){
    return this.HttpClient.get<any>(`${this.baseURL}/debts?username=${username}`);
  }

  getSpentById(id: string){
    return this.HttpClient.get<any>(`${this.baseURL}/id/${id}`);
  }

  getSpentsByUsername(username: string){
    return this.HttpClient.get<any>(`${this.baseURL}/byUsername?username=${username}`);
  }

  getDebtsBySpentId(id: string){
    return this.HttpClient.get<any>(`${this.baseURL}/debts/id/${id}`);
  }

  getAllSpentByHomeId(id: string){
    return this.HttpClient.get<any>(`${this.baseURL}/byHomeId/${id}`);
  }
  // @PutMapping("/debt/{id}/paid")
  paidDebt(id: string, paid: boolean){
    return this.HttpClient.put(`${this.baseURL}/debt/${id}/paid`, paid);
  }

  deleteSpentAndDebts(id: string){
    return this.HttpClient.delete(`${this.baseURL}/${id}/delete`);
  }

}
