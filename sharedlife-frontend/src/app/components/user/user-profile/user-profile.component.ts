import { HomeService } from './../../../services/home/home.service';
import { HomeCreateDTO } from './../../../models/home/home-create-dto/home-create-dto';
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
  isAdmin: boolean = false;
  authorities: string[] = [];
  username: string = "";
  user: User;
  homes: any[] = [];
  constructor(
    private TokenService: TokenService,
    private UserService: UserService,
    private HomeService: HomeService) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.username = this.TokenService.getUserName();
      // INFORMACION DEL PERFIL DEL USUARIO => extraer a metodo
      this.UserService.getUserByUsername(this.username).subscribe(
        data => {
          this.user = data;
        },
        error => {
          console.log("Error user can't not found");
      });
      // INFORMACION PARA LA TABLA DE VIVIENDAS => extraer a metodo
      this.authorities = this.TokenService.getAuthorities();
      console.log(this.authorities);
      this.isAdmin = false;
      this.authorities.forEach(role => {
        if(role.indexOf('ROLE_ADMIN') === 0){
          this.isAdmin = true;
        }
      })
      if(this.isAdmin){
        this.HomeService.getHouseByUsername(this.username).subscribe(
          data => {
            this.homes = data;
            console.log(data);
          }
        )
      }
    }
    else{
      this.username = "";
      console.log("Error token doesn't exists");
    }
  }



}
