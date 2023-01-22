import { Component, Input, OnInit } from '@angular/core';
import { Spent } from './../../../models/spent/spent';
import { SpentService } from './../../../services/spent/spent.service';
import { User } from 'src/app/models/user/user';
import { Debt } from './../../../models/Debt/debt';
import { UserService } from './../../../services/user/user.service';
import { DebtInfo } from 'src/app/models/Debt/debtInfo';

@Component({
  selector: 'app-spent-table',
  templateUrl: './spent-table.component.html',
  styleUrls: ['./spent-table.component.css'],
})
export class SpentTableComponent {
  @Input() username: string;
  @Input() idHome: string;
  spents: Spent[] = [];
  displayStyleA = 'none';
  debtsUsers: User[] = [];
  debtsA: Debt[] = [];
  debtUser: User;
  debtsInfo: DebtInfo[] = [];

  constructor(
    private SpentService: SpentService,
    private UserService: UserService
  ) {}

  ngOnInit(): void {
    this.SpentService.getSpentsByUsernameAndHomeId(
      this.username,
      this.idHome
    ).subscribe(
      (data) => {
        this.spents = data;
        console.log(this.spents);
        console.log('OK getting the spents of the user');
      },
      (error) => {
        console.log('ERROR getting the spents of the user');
      }
    );
  }

  closePopupA() {
    this.displayStyleA = 'none';
  }

  getDebtsById(id: string) {
    this.displayStyleA = 'block';
    this.SpentService.getDebtsInfo(id).subscribe(
      (data) => {
        console.log('debts info ok');
        this.debtsInfo = data['verifyPaidDTOList'];
        console.log(this.debtsInfo);
      },
      (error) => {
        console.log('debts info err');
      }
    );
    //this.debtsA = [];
    //this.debtsUsers = [];
  }
}
