import { UserService } from './../../../services/user/user.service';
import { TaskService } from './../../../services/task/task.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HomeService } from './../../../services/home/home.service';
import { TokenService } from './../../../services/token/token.service';
import { Component, OnInit } from '@angular/core';
import { Home } from 'src/app/models/home/home';
import { User } from 'src/app/models/user/user';
import { Task } from 'src/app/models/task/task';

@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css']
})
export class TaskCreateComponent implements OnInit {

  authorities: string[] = [];
  username: string;
  homes: Home[];
  users: User[] = [];
  isAdmin: boolean = false;
  userSelect: User;
  newTask: Task;
  createTaskForm = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    user: new FormControl(null),
  });
  constructor(
    private TokenService: TokenService,
    private HomeService: HomeService,
    private TaskService: TaskService,
    private UserService: UserService,
  ) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.username = this.TokenService.getUserName();
      this.authorities = this.TokenService.getAuthorities();
      this.isAdmin = false;
      this.authorities.forEach(role => {
        if(role.indexOf('ROLE_ADMIN') === 0){
          this.isAdmin = true;
        }
      });
      if(this.isAdmin == false){
        // Obtencion de la información de los miembros de la vivienda del usuario logeado
        this.HomeService.getHouseByUsername(this.username).subscribe(
          data => {
            this.homes = data;
            console.log(this.homes[0]);
            console.log(this.homes);
            this.HomeService.getAllHomeMembers(this.homes[0].id.toString()).subscribe(
              data => {
                this.users = data;
                console.log(this.users);
              },
              error => {
                console.log("ERROR getting the home members");
              }
            )
          },
          error => {
            console.log("ERROR getting the house of the user");
          }
        );
      }
    }
  }

  createTask(){
    console.log(this.createTaskForm.value);
    this.newTask = new Task(
      this.createTaskForm.value['title'],
      this.createTaskForm.value['description'],
      '',
      '',
      false,
      this.createTaskForm.value['user'],
      );
      this.TaskService.createTask(this.newTask).subscribe(
        data => {
          console.log("Task created OK");
        },
        error => {
          console.log("Task created error");
        }
      );
    window.location.reload();
  }

}
