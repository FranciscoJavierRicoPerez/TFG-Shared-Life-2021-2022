import { ActivatedRoute } from '@angular/router';
import { SpentService } from './../../../services/spent/spent.service';
import { HomeService } from './../../../services/home/home.service';
import { TokenService } from './../../../services/token/token.service';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Home } from 'src/app/models/home/home';
import { User } from 'src/app/models/user/user';
import { Spent } from 'src/app/models/spent/spent';

@Component({
  selector: 'app-spent-create',
  templateUrl: './spent-create.component.html',
  styleUrls: ['./spent-create.component.css']
})
export class SpentCreateComponent implements OnInit {

  spentCreateForm = new FormGroup({
    title: new FormControl('', Validators.required),
    description: new FormControl('', Validators.required),
    totalPrice: new FormControl('', Validators.required)
  });

  authorities: string[] = [];
  username: string;
  isAdmin: boolean;
  homes: Home[];
  idHome: string;
  users: User[] = [];
  spent: Spent;
  spents: Spent[] = [];
  home: Home;
  constructor(
    private TokenService: TokenService,
    private HomeService: HomeService,
    private SpentService: SpentService,
    private ActivatedRoute: ActivatedRoute) { }

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

      this.idHome = this.ActivatedRoute.snapshot.params['id'];
      this.HomeService.getHomeById(this.idHome).subscribe(
        data => {
          this.home = data;
          this.HomeService.getAllHomeMembers(this.idHome).subscribe(
            data => {
              this.users = data;
              console.log(this.users);
            },
            error => {
              console.log("ERROR getting the home members");
            }
          );
          // OBTENDCION DE TODOS LOS GASTOS DE LA VIVIENDA
          this.SpentService.getAllSpentByHomeId(this.idHome).subscribe(
            data => {
              this.spents = data;
              console.log(this.spents);
              console.log("OK Getting all spent of the home");
            },
            error =>{
              console.log("ERR Getting all spents of the home");
            }
          );
        },
        error => {
          console.log("Error getting the information of the home with id: " + this.idHome);
        }
      );
    }
    else{
      console.log("ERR logging");
    }
  }

  createSpent(){
    console.log(this.spentCreateForm.value)
    this.spent = new Spent(
      '',
      this.spentCreateForm.value['title'],
      this.spentCreateForm.value['description'],
      this.spentCreateForm.value['totalPrice'],
      this.username,
      this.idHome,
      false
    );
    console.log(this.spent)
    this.SpentService.createSpent(this.spent,this.isAdmin).subscribe(
      data => {
        console.log("Creating of the spent succesfully")
      },
      error => {
        console.log("Creating of the spent error")
      }
    )
    window.location.reload();
  }

}
