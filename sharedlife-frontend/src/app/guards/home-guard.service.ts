import { TokenService } from './../services/token/token.service';
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
// GUARDA PARA LA CREACIÓN DE LA VIVIENDA

// EL OBJETIVO DE ESTA CLASE ES LA COMPROBACION DE LOS ROLES ANTES DE ACCEDER A LA RUTA
export class HomeGuardService implements CanActivate{

  realRole: string; // USER O ADMIN

  constructor(
    private TokenService: TokenService,
    private Router: Router) { }
  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    const expectedRole = route.data['expectRole'];
    const roles = this.TokenService.getAuthorities();
    this.realRole = 'user';
    //console.log("expectedRole => "+ expectedRole);
   // console.log("roles => " + roles );
    roles.forEach(role => {
      if(role === 'ROLE_ADMIN'){
        this.realRole = 'admin';
      }
    });
    if(!this.TokenService.getToken() || expectedRole.indexOf(this.realRole) === -1){
      this.Router.navigate(['/']);
      return false;
    }
    else{
      return true;
    }
  }
}
