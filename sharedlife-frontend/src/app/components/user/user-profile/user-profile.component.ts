import { TokenService } from './../../../services/token/token.service';
import { UserService } from './../../../services/user/user.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/models/user/user';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.css']
})
export class UserProfileComponent implements OnInit {

  username: string;
  user: User;
  constructor(
    private TokenService: TokenService,
    private UserService: UserService) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.username = this.TokenService.getUserName();
      this.UserService.getUserByUsername(this.username).subscribe(
        data => {
          this.user = data;
        },
        error => {
          console.log("Error user can't not found");
        });
    }
    else{
      this.username = "";
      console.log("Error token doesn't exists");
    }
  }



}
