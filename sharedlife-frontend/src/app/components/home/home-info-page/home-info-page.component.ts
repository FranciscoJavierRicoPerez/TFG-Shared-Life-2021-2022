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
import { Component, Input, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user/user';

@Component({
  selector: 'app-home-info-page',
  templateUrl: './home-info-page.component.html',
  styleUrls: ['./home-info-page.component.css'],
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
  displayStyle = 'none';
  spents: Spent[] = [];
  debtsUsers: User[] = [];
  errorSendInvitation: boolean;
  alreadyInvited: boolean;
  userNotExists: boolean;
  renters: User[] = [];
  errorMessage: string = '';
  successMessage: string = '';
  invitationForm = new FormGroup({
    username: new FormControl('', [Validators.required]),
  });
  constructor(
    private HomeService: HomeService,
    private Router: Router,
    private ActivatedRoute: ActivatedRoute,
    private TokenService: TokenService,
    private SpentService: SpentService
  ) {}

  ngOnInit(): void {
    if (this.TokenService.getToken()) {
      this.username = this.TokenService.getUserName();
      this.authorities = this.TokenService.getAuthorities();
      this.authorities.forEach((role) => {
        if (role.indexOf('ROLE_ADMIN') === 0) {
          this.isAdmin = true;
        }
      });
      this.idHome = this.ActivatedRoute.snapshot.params['id'];
      this.HomeService.getHomeById(this.idHome).subscribe((data) => {
        this.home = data;
      });
      this.HomeService.getAllHomeMembers(this.idHome).subscribe((data) => {
        this.users = data;
        this.renters = data;
        console.log(this.users);
      });
    }
  }

  sendInvitation(): void {
    this.successMessage = '';
    this.errorMessage = '';
    this.invitation = new Invitation(
      this.invitationForm.value['username'],
      this.idHome,
      '',
      this.home.address
    );
    this.HomeService.sendInvitation(this.invitation).subscribe(
      (data) => {
        console.log('Invitation send ok');
        this.successMessage = "La invitación ha sido enviada correctamente";
        // window.location.reload();
      },
      (err) => {
        console.log('Invitation send err');
        if (err.status == 401) {
          this.alreadyInvited = true;
          this.errorMessage = 'El usuario ya tiene una invitación pendiente';
        } else {
          if (err.status == 503) {
            this.userNotExists = true;
            this.errorMessage = 'No existe ningun usuario con ese nombre';
          } else {
            this.errorSendInvitation = true;
            this.errorMessage = 'Este usuario ya esta registrado en otra vivienda';
          }
        }
      }
    );
  }

  leaveHouse() {
    console.log(this.idHome);
    this.HomeService.leaveHome(this.idHome, this.username).subscribe(
      (data) => {
        console.log('User leave home sucessfully');
        this.Router.navigate(['/']);
      },
      (error) => {
        console.log('Error leaving home');
      }
    );
  }

  deleteHouse() {
    console.log(this.idHome);
    this.HomeService.deleteHome(this.idHome).subscribe(
      (data) => {
        console.log('home delete successfully');
        this.Router.navigate(['/']);
      },
      (err) => {
        console.log('home delete error');
      }
    );
  }

  deleteSpentAndDebts(idSpent: string) {
    console.log(idSpent);
    this.SpentService.deleteSpentAndDebts(idSpent).subscribe(
      (data) => {
        console.log('spent delete successfully');
      },
      (error) => {
        console.log('error deleting spents');
      }
    );
  }
}
