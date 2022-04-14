import { TokenService } from './../../services/token/token.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home-page',
  templateUrl: './home-page.component.html',
  styleUrls: ['./home-page.component.css']
})
export class HomePageComponent implements OnInit {

  isLogged: boolean = false;
  username: string;
  constructor(
    private TokenService: TokenService) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.isLogged = true;
      this.username = this.TokenService.getUserName();
    }
    else{
      this.isLogged = false;
      this.username = "";
    }
  }

}
