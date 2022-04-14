import { TokenService } from './../../services/token/token.service';
import { Component, OnInit } from '@angular/core';

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
  constructor(
    private TokenService: TokenService) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.isLogged = true;
      this.username = this.TokenService.getUserName();
      this.authorities = this.TokenService.getAuthorities();
      console.log(this.authorities);
      this.authorities.forEach(role => {
        if(role.indexOf('ROLE_ADMIN') === 0){
          this.isAdmin = true;
        }
      })
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

}

