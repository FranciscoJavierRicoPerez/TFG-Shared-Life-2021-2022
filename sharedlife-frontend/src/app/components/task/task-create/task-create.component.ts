import { ActivatedRoute } from '@angular/router';
import { UserService } from './../../../services/user/user.service';
import { TaskService } from './../../../services/task/task.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HomeService } from './../../../services/home/home.service';
import { TokenService } from './../../../services/token/token.service';
import { Component, OnInit } from '@angular/core';
import { Home } from 'src/app/models/home/home';
import { User } from 'src/app/models/user/user';
import { Task } from 'src/app/models/task/task';
import { TaskTrakingStatusDTO } from 'src/app/models/taskTraking/TaskTrakingStatusDTO';

@Component({
  selector: 'app-task-create',
  templateUrl: './task-create.component.html',
  styleUrls: ['./task-create.component.css'],
})
export class TaskCreateComponent implements OnInit {
  authorities: string[] = [];
  username: string;
  homes: Home[];
  users: User[] = [];
  isAdmin: boolean = false;
  userSelect: User;
  newTask: Task;
  idHome: string;
  allHomeTasks: Task[] = [];
  weeklyTasks: Task[] = [];
  homeTasks: Task[] = [];
  home: Home;
  taskTrakingStatus: TaskTrakingStatusDTO;
  allTaskConfirmed: boolean = false;
  weeklyTaskIds: string[] = [];

  infoModal: boolean = false;

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
    private ActivatedRoute: ActivatedRoute
  ) {}

  ngOnInit(): void {
    if (this.TokenService.getToken()) {
      this.username = this.TokenService.getUserName();
      this.authorities = this.TokenService.getAuthorities();
      this.isAdmin = false;
      this.authorities.forEach((role) => {
        if (role.indexOf('ROLE_ADMIN') === 0) {
          this.isAdmin = true;
        }
      });
      this.idHome = this.ActivatedRoute.snapshot.params['id'];
      this.HomeService.getHomeById(this.idHome).subscribe(
        (data) => {
          this.home = data;
          this.HomeService.getAllHomeMembers(this.idHome).subscribe(
            (data) => {
              this.users = data;
              console.log(this.users);
            },
            (error) => {
              console.log('ERROR getting the home members');
            }
          );
          this.TaskService.getAllTaskByHomeId(
            this.idHome,
            this.username
          ).subscribe(
            (data) => {
              this.allHomeTasks = data;
              // Ahora del allHomeTasks puede dividirlo en tareas semanales y normales
              for (var task of this.allHomeTasks) {
                if (task.weekTask) {
                  this.weeklyTasks.push(task);
                } else {
                  this.homeTasks.push(task);
                }
              }
              console.log('Getting all task home OK');
              this.TaskService.checkTaskTraking(this.username).subscribe(
                (data) => {
                  console.log('OK checking the task traking');
                  this.taskTrakingStatus = data;
                  console.log(this.taskTrakingStatus);
                },
                (err) => {
                  console.log('ERR checking the task traking');
                }
              );

              // Hacer una comprobacion de si todas las tarea semanales de todos los miembros de la vivienda tienen todas las confirmaciones
              // SI LAS TIENE SE BORRAN TODOS LOS TRAKING Y SE REINICA LAS TAREAS
              // necesitp una funcion que devuelva un booleano indicando eso va a recibir por parametro
              for (let i = 0; i < this.weeklyTasks.length; i++) {
                this.weeklyTaskIds.push(this.weeklyTasks[i].id);
              }
              this.TaskService.checkIfAllTaskAreConfirmed(
                this.weeklyTaskIds
              ).subscribe(
                (data) => {
                  debugger;
                  console.log('OK checking if all weeklyTask are confirmed');
                  this.allTaskConfirmed = data;
                  console.log(this.allTaskConfirmed);
                  if (this.allTaskConfirmed) {
                    debugger;
                    console.log(
                      'Starting the process restart of the weekly tasks'
                    );
                    this.TaskService.restartWeeklyTasks(
                      this.weeklyTaskIds
                    ).subscribe(
                      (data) => {
                        console.log('OK weekly task reasigned');
                        debugger;
                        this.HomeService.manageWeeklyTask(
                          this.idHome,
                          this.weeklyTaskIds
                        ).subscribe(
                          (data) => {
                            debugger;
                            console.log('Weekly task reasigned');
                            //window.location.reload();
                          },
                          (err) => {
                            debugger;
                            console.log('ERR reasinating weekly task');
                          }
                        );
                      },
                      (err) => {
                        console.log(
                          'ERR in the reasicnation of the weeklyTask'
                        );
                      }
                    );
                  }
                },
                (err) => {
                  console.log('ERR checking if all weekly task are confirmed');
                }
              );
            },
            (error) => {
              console.log('Getting al task home ERROR');
            }
          );
        },
        (error) => {
          console.log('ERROR getting the house with id: ' + this.idHome);
        }
      );
    }
  }

  createTask(idHome: string) {
    console.log(this.createTaskForm.value);
    this.newTask = new Task(
      '',
      this.createTaskForm.value['title'],
      this.createTaskForm.value['description'],
      '',
      '',
      false,
      this.createTaskForm.value['user'],
      idHome
    );
    this.TaskService.createTask(this.newTask).subscribe(
      (data) => {
        console.log('Task created OK');
      },
      (error) => {
        console.log('Task created error');
      }
    );
    window.location.reload();
  }

  correctDate(date: string) {
    var auxDate = date.split('T');
    return auxDate[0];
  }

  openTaskInfoModal() {
    this.infoModal = true;
  }

  confirmTaskCompleted(idTask: string) {
    // Paso como pareametro el nombre del usuario que confirma que la tarea esta completa
    console.log(
      'The user ' +
        this.username +
        ' confirms the task ' +
        idTask +
        ' is completed'
    );
    // MANDAR TAMBIEN TODOS LOS MEMBERS this.users
    this.TaskService.confirmTaskCompleted(
      this.username,
      idTask,
      this.users
    ).subscribe(
      (data) => {
        console.log('Task completed succesfully');
        console.log(data);
        window.location.reload();
      },
      (error) => {
        console.log('Error completing task');
      }
    );
  }
}
