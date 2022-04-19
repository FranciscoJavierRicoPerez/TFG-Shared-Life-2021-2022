import { TaskService } from './../../../services/task/task.service';
import { TokenService } from './../../../services/token/token.service';
import { Invitation } from './../../../models/invitation/invitation';
import { Router, ActivatedRoute } from '@angular/router';
import { HomeCreateDTO } from 'src/app/models/home/home-create-dto/home-create-dto';
import { HomeService } from './../../../services/home/home.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user/user';

@Component({
  selector: 'app-home-info-page',
  templateUrl: './home-info-page.component.html',
  styleUrls: ['./home-info-page.component.css']
})
export class HomeInfoPageComponent implements OnInit {

  username: string;
  isAdmin: boolean;
  invitation: Invitation;
  home: HomeCreateDTO;
  idHome: string;
  authorities: string[] = [];
  users: User[] = [];
  tasks: Task[] = [];

  invitationForm = new FormGroup({
    username: new FormControl('', [Validators.required])
  })

  constructor(
    private HomeService: HomeService,
    private Router: Router,
    private ActivatedRoute: ActivatedRoute,
    private TokenService: TokenService,
    private TaskService: TaskService
  ) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.username = this.TokenService.getUserName();
      this.authorities = this.TokenService.getAuthorities();
      this.authorities.forEach(role => {
        if(role.indexOf('ROLE_ADMIN') === 0){
          this.isAdmin = true;
        }
      })
      this.idHome = this.ActivatedRoute.snapshot.params['id'];
      this.HomeService.getHomeById(this.idHome).subscribe(
        data => {
          this.home = data;
        }
      );
      this.HomeService.getAllHomeMembers(this.idHome).subscribe(
        data => {
          this.users = data;
          console.log(this.users);
        }
      );

      // OBTENCION DE LAS TODAS LAS TAREAS DE UN USUARIO
      this.TaskService.getAllTaskByUsername(this.username).subscribe(
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
  }

  sendInvitation(): void{
    console.log(this.invitationForm.value)
    console.log(this.idHome);
    this.invitation = new Invitation(
      this.invitationForm.value['username'],
      this.idHome,
      '',
      this.home.address
    );
    this.HomeService.sendInvitation(this.invitation).subscribe(
      data => {
        console.log("Invitation send ok");
      },
      err => {
        console.log("Invitation send err");
      }
    );
    window.location.reload();
  }

}
