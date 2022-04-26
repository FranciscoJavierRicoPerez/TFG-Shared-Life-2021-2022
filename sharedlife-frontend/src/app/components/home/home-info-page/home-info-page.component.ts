import { UserService } from './../../../services/user/user.service';
import { Spent } from './../../../models/spent/spent';
import { SpentService } from './../../../services/spent/spent.service';
import { Debt } from './../../../models/Debt/debt';
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
  debts: Debt[] = [];
  spent: Spent;
  displayStyle = "none";
  displayStyleA = "none";
  spents: Spent[] = [];
  debtsA: Debt[] = [];
  debtsUsers: User[] = [];
  debtUser: User;
  invitationForm = new FormGroup({
    username: new FormControl('', [Validators.required])
  })

  constructor(
    private HomeService: HomeService,
    private Router: Router,
    private ActivatedRoute: ActivatedRoute,
    private TokenService: TokenService,
    private TaskService: TaskService,
    private SpentService: SpentService,
    private UserService: UserService
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

      // OBTENER LAS DEUDAS QUE TIENE PENDIENTE EL USUARIO
      this.SpentService.getAllDebtsByUsername(this.username).subscribe(
        data => {
          this.debts = data;
          console.log(this.debts);
          console.log("OK getting the debts of the user");
        },
        error => {
          console.log("ERROR getting the debts of the user");
        }
      );

      // INICIALIZAMOS EL SPENT PARA QUE NO DE ERROR???
      this.spent = new Spent('','','','','','');

      // OBTENEMOS TODOS LOS  GASTOS PUBLICADOS POR EL USUARIO
      this.SpentService.getSpentsByUsername(this.username).subscribe(
        data => {
          this.spents = data;
          console.log(this.spents);
          console.log("OK getting the spents of the user");
        },
        error => {
          console.log("ERROR getting the spents of the user");
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

  getSpentById(id: string){
    this.displayStyle = "block";
    //this.spent = new Spent('','','','','','');
    this.SpentService.getSpentById(id).subscribe(
      data => {
        this.spent = data;
        console.log(this.spent);
        console.log("OK Getting the spent with id " + id);
      },
      error =>{
        console.log("ERR getting the spent with id " + id);
      }
    );
  }

  getDebtsById(id: string){
    this.displayStyleA = "block";
    this.SpentService.getDebtsBySpentId(id).subscribe(
      data => {
        this.debtsA = data;
        console.log(this.debtsA);
        console.log("OK Getting the debts");
        // AQUI AHORA TENGO QUE OBTENER TODOS LOS USUARIO DE CADA UNO DE LOS DEBTS
        this.debtsA.forEach(debt => {
          this.UserService.getUserById(Number(debt['idUser'])).subscribe(
            data => {
              this.debtUser = data;
              this.debtsUsers.push(this.debtUser);
              console.log(this.debtsUsers);
            },
            error => {
              console.log("ERR Getting de users debters");
            }
          );
        });
      },
      error => {
        console.log("ERR getting the debts");
      }
    );
    this.debtsA = [];
    this.debtsUsers = [];
  }

  closePopup() {
    this.displayStyle = "none";
  }

  closePopupA() {
    this.displayStyleA = "none";
  }

}
