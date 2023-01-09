import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ConfirmedTaskDTO } from 'src/app/models/taskTraking/confirmedTaskDTO';
import { User } from 'src/app/models/user/user';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  private baseURL = 'http://localhost:8080/tasks';

  constructor(private HttpClient: HttpClient) {}

  createTask(task: any): Observable<Object> {
    return this.HttpClient.post(`${this.baseURL}/create`, task);
  }

  getAllTaskByUsernameAndHomeId(username: string, id: string): Observable<any> {
    return this.HttpClient.get<any>(
      `${this.baseURL}/byHomeId/${id}/byUsername?username=${username}`
    );
  }

  getAllTaskByHomeId(id: string, username: string): Observable<any> {
    return this.HttpClient.get<any>(`${this.baseURL}/byHomeId/${id}`);
  }

  updateFinishedStatus(id: string, finished: boolean) {
    return this.HttpClient.put(`${this.baseURL}/${id}/finished`, finished);
  }

  deleteTask(id: string) {
    return this.HttpClient.delete(`${this.baseURL}/${id}`);
  }

  getWeeklyTasksByHomeId(id: string) {
    return this.HttpClient.get<any>(`${this.baseURL}$/home/${id}/weeklyTasks`);
  }

  /* startTaskProgress(id: string) {
    return this.HttpClient.post<any>(`${this.baseURL}/modifyTaskProgress`, id);
  } */
  startTaskProgress(id: string, username: string, renters: User[]) {
    return this.HttpClient.post<any>(
      `${this.baseURL}/modifyTaskProgress`,
      new ConfirmedTaskDTO(id, username, renters)
    );
  }
  confirmTaskCompleted(username: string, id: string, users: User[]) {
    return this.HttpClient.post<any>(
      `${this.baseURL}/modifyTaskProgress`,
      new ConfirmedTaskDTO(id, username, users)
    );
  }

  checkTaskTraking(username: string) {
    return this.HttpClient.get<any>(
      `${this.baseURL}/checkTaskTraking?username=${username}`
    );
  }
}
