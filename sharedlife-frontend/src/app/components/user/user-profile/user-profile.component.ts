import { Home } from 'src/app/models/home/home';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { Invitation } from './../../../models/invitation/invitation';
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
  invitations: Invitation[] = [];
  displayStyle = "none";
  invitation: Invitation;
  members: User[] = [];
  userHomes: Home[] = [];
  acceptInvitationForm = new FormGroup({
    homeCode: new FormControl('', [Validators.required])
  });

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
          console.log(this.user);
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
      // INFORMACION DE LAS INVITACIONES => extraer a metodos
      if(this.isAdmin == false){
        this.UserService.getInvitationsByUsername(this.username).subscribe(
          data => {
            this.invitations = data;
            console.log(this.invitations);
          }
        );
      }
      // OBTENCION PARA EL USUARIO DE TODOS LOS HABITANTES DE UNA VIVIENDA
      if(this.isAdmin == false && this.invitations.length == 0){
        this.HomeService.getHouseByUsername(this.username).subscribe(
          data => {
            this.homes = data;
            console.log(this.homes);
          }
        )
      }
    }
    else{
      this.username = "";
      console.log("Error token doesn't exists");
    }
  }

  openPopup() {
    this.displayStyle = "block";
  }
  closePopup() {
    this.displayStyle = "none";
  }

  sendConfirmationInvitation(idHome: string){
    console.log(this.acceptInvitationForm.value);
    console.log("prueba =>>>>" + idHome);
    this.invitation = new Invitation(this.username,idHome, this.acceptInvitationForm.value['homeCode'], '');
    console.log(this.invitation);
    this.HomeService.acceptInvitation(this.invitation).subscribe(
      data => {
        console.log("Invitation accept OK");
      },
      error => {
        console.log("Invitation accept ERROR");
      });
    window.location.reload();
  }

}
