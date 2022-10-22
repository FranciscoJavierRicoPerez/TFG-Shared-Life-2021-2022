import { Component, Input, OnInit, Output } from '@angular/core';
import { TaskService } from './../../../services/task/task.service';

@Component({
  selector: 'app-task-table',
  templateUrl: './task-table.component.html',
  styleUrls: ['./task-table.component.css']
})
export class TaskTableComponent implements OnInit {

  @Input() username: string;
  @Input() idHome: string;

  tasks: Task[] = [];

  constructor(private TaskService: TaskService) { }

  ngOnInit(): void {
    console.log("CARGANDO TASK TABLE")
    // OBTENCION DE LAS TODAS LAS TAREAS DE UN USUARIO
    this.TaskService.getAllTaskByUsernameAndHomeId(this.username, this.idHome).subscribe(
      data => {
        this.tasks = data;
        console.log(this.tasks);
        console.log("OK getting all tasks by username");
      },
      error => {
        console.log("ERROR getting all tasks by username");
      }
    );
  }

  correctDate(date : string){
    var auxDate = date.split("T");
    return auxDate[0];
  }

  deleteTask(idTask: string){
    this.TaskService.deleteTask(idTask).subscribe(
      data => {
        console.log("delet task successfully");
        window.location.reload();
      },
      error => {
        console.log("error deleting task");
      }
    );
  }

  updateFinishedStatus(id: string){
    console.log("Update the task with id: " + id);
    this.TaskService.updateFinishedStatus(id, true).subscribe(
      data => {
        console.log("Update finished task OK");
      },
      error => {
        console.log("Update finished task ERROR");
      }
    );
    window.location.reload();
  }

}
