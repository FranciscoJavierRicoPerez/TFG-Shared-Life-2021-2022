import { Component, Input, OnInit } from '@angular/core';
import { Spent } from './../../../models/spent/spent';
import { SpentService } from './../../../services/spent/spent.service';
import { User } from 'src/app/models/user/user';
import { Debt } from './../../../models/Debt/debt';
import { UserService } from './../../../services/user/user.service';


@Component({
  selector: 'app-spent-table',
  templateUrl: './spent-table.component.html',
  styleUrls: ['./spent-table.component.css']
})
export class SpentTableComponent{

  @Input() username: string;
  @Input() idHome: string;
  spents: Spent[] = [];
  displayStyleA = "none";
  debtsUsers: User[] = [];
  debtsA: Debt[] = [];
  debtUser: User;

  constructor(
    private SpentService: SpentService,
    private UserService: UserService) { }

  ngOnInit(): void {
    this.SpentService.getSpentsByUsernameAndHomeId(this.username, this.idHome).subscribe(
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

  closePopupA() {
    this.displayStyleA = "none";
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

}
