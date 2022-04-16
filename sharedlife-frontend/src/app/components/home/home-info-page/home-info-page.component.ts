import { Invitation } from './../../../models/invitation/invitation';
import { Router, ActivatedRoute } from '@angular/router';
import { HomeCreateDTO } from 'src/app/models/home/home-create-dto/home-create-dto';
import { HomeService } from './../../../services/home/home.service';
import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-home-info-page',
  templateUrl: './home-info-page.component.html',
  styleUrls: ['./home-info-page.component.css']
})
export class HomeInfoPageComponent implements OnInit {

  invitation: Invitation;
  home: HomeCreateDTO;
  idHome: string;
  invitationForm = new FormGroup({
    username: new FormControl('', [Validators.required])
  })

  constructor(
    private HomeService: HomeService,
    private Router: Router,
    private ActivatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.idHome = this.ActivatedRoute.snapshot.params['id'];
    this.HomeService.getHomeById(this.idHome).subscribe(
      data => {
        this.home = data;
      }
    );
  }

  sendInvitation(): void{
    console.log(this.invitationForm.value)
    console.log(this.idHome);
    this.invitation = new Invitation(
      this.invitationForm.value['username'],
      this.idHome
    );
    this.HomeService.sendInvitation(this.invitation).subscribe(
      data => {
        console.log("Invitation send ok");
      },
      err => {
        console.log("Invitation send err");
      }
    );
  }

}
