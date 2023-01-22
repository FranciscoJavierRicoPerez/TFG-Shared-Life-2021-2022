import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user/user';
import { TokenService } from 'src/app/services/token/token.service';
import { UserService } from 'src/app/services/user/user.service';

@Component({
  selector: 'app-member-profile',
  templateUrl: './member-profile.component.html',
  styleUrls: ['./member-profile.component.css']
})
export class MemberProfileComponent implements OnInit {

  memberName: string = "";
  member: User | null = null;
  constructor(
    private TokenService: TokenService,
    private UserService: UserService,
    private Router: Router,
    private ActivatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.memberName = this.ActivatedRoute.snapshot.params['username'];
      this.UserService.getUserByUsername(this.memberName).subscribe(
        data => {
          console.log("Member obtained succesfully");
          this.member = data;
          console.log(this.member);
        },
        error => {
          console.log("Err getting the memeber");
        }
      );
    }
  }

}
