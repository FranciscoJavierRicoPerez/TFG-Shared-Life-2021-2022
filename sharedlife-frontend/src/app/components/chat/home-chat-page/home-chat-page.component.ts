import { Component, OnInit } from '@angular/core';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-home-chat-page',
  templateUrl: './home-chat-page.component.html',
  styleUrls: ['./home-chat-page.component.css']
})
export class HomeChatPageComponent implements OnInit {

  username: string = "";

  constructor(
    private TokenService: TokenService
  ) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.username = this.TokenService.getUserName();
    }
  }

}
