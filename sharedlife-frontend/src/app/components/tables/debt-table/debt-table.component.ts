import { Component, Input, OnInit } from '@angular/core';
import { Debt } from 'src/app/models/Debt/debt';
import { Spent } from 'src/app/models/spent/spent';
import { SpentService } from 'src/app/services/spent/spent.service';

@Component({
  selector: 'app-debt-table',
  templateUrl: './debt-table.component.html',
  styleUrls: ['./debt-table.component.css']
})
export class DebtTableComponent implements OnInit {

  @Input() username: string;
  debts: Debt[] = [];
  displayStyle = "none";
  spent: Spent = new Spent();
  constructor(private SpentService: SpentService) { }

  ngOnInit(): void {
    console.log("USERNAME DESDE TABLE DEBTS COMPONENTE => " + this.username);
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
  }

  getSpentById(id: string){
    this.displayStyle = "block";
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

  closePopup() {
    this.displayStyle = "none";
  } 

  paidDebt(id: string, paid: boolean){
    console.log(id)
    console.log(paid);
    this.SpentService.paidDebt(id, paid).subscribe(
      data => {
        console.log("OK change paid")
      },
      error => {
        console.log("ERR change paid")
      }
    );
    window.location.reload();
  }

}
