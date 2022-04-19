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
}
