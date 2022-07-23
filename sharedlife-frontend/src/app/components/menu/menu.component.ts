import { Home } from 'src/app/models/home/home';
import { HomeService } from './../../services/home/home.service';
import { TokenService } from './../../services/token/token.service';
import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user/user';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  isLogged = false;
  username = '';
  isAdmin = false;
  authorities: string[] = [];
  homes: Home[] = [];
  userHasHome = false;
  mapHomeMembers = new Map();
  members: User[] = [];
  constructor(
    private TokenService: TokenService,
    private HomeService: HomeService) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.isLogged = true;
      this.username = this.TokenService.getUserName();
      this.authorities = this.TokenService.getAuthorities();
      console.log(this.authorities);
      this.isAdmin = false;
      this.authorities.forEach(role => {
        if(role.indexOf('ROLE_ADMIN') === 0){
          this.isAdmin = true;
        }
      })

      this.HomeService.userHasHome(this.username).subscribe(
        data => {
          this.userHasHome = data;
          if(this.userHasHome == true){
            this.HomeService.getHouseByUsername(this.username).subscribe(
              dataHomes => {
                this.homes = dataHomes;
                console.log(this.homes);
              },
              error => {
                console.log("Error getting houses for user " + this.username);
              }
            )
          }
        }
      )
    }
    else{
      this.isLogged = false;
      this.username = '';
      this.isAdmin = false;
    }
  }

  onLogOut():void{
    this.TokenService.logout();
    window.location.reload();
    window.location.href='/inicio'; // SOLUCIÓN BUG: AL HACER LOGOUT SE PERMANECIA EN LA
                                    // EN LA PAGINA QUE ESTABA EN VEZ DE REDIRIGIN A LA PRINCIPAL
  }

  countHomeMembers(idHome: string){
    this.HomeService.getAllHomeMembers(idHome).subscribe(
      data => {
        this.members = data;
        console.log("Cantidad de miembros => " + this.members.length + " en la vivienda " + idHome);
        return this.members.length;
      },
      error => {
        console.log('Error getting the members of the home');
      }
    );
  }

}

