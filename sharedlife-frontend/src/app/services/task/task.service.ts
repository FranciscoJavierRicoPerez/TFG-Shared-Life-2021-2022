import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  private baseURL = "http://localhost:8080/tasks";

  constructor(private HttpClient: HttpClient) { }

  createTask(task: any): Observable<Object>{
    return this.HttpClient.post(`${this.baseURL}/create`, task);
  }

  getAllTaskByUsernameAndHomeId(username: string, id: string): Observable<any>{
    return this.HttpClient.get<any>(`${this.baseURL}/byHomeId/${id}/byUsername?username=${username}`);
  }

  getAllTaskByHomeId(id: string, username:string): Observable<any>{
    return this.HttpClient.get<any>(`${this.baseURL}/byHomeId/${id}`);
  }

  updateFinishedStatus(id: string, finished: boolean){
    return this.HttpClient.put(`${this.baseURL}/${id}/finished`, finished);
  }

  deleteTask(id: string){
    return this.HttpClient.delete(`${this.baseURL}/${id}`);
  }
}
