import { TokenService } from './../../../services/token/token.service';
import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HomeService } from './../../../services/home/home.service';
import { Component, OnInit } from '@angular/core';
import { Home } from 'src/app/models/home/home';
import { HomeCreateDTO } from 'src/app/models/home/home-create-dto/home-create-dto';

@Component({
  selector: 'app-home-create',
  templateUrl: './home-create.component.html',
  styleUrls: ['./home-create.component.css']
})
export class HomeCreateComponent implements OnInit {

  token: string;
  home: HomeCreateDTO;

  myForm = new FormGroup({
    address: new FormControl('', Validators.required),
    floor: new FormControl('', Validators.required),
    number: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required),
    rooms: new FormControl('', Validators.required)
  });

  constructor(
    private HomeService: HomeService,
    private Router: Router,
    private TokenService: TokenService) {}

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.token = this.TokenService.getToken();
    }
    else{
      this.token = "";
    }
  }

  saveHome(){
    this.home = new HomeCreateDTO(
      this.myForm.value.address,
      this.myForm.value.floor,
      this.myForm.value.number,
      this.myForm.value.city,
      this.myForm.value.country,
      this.myForm.value.rooms,
      this.token,
      false);
    this.HomeService.createHouse(this.home).subscribe(
    data => {
      console.log(data);
    }, error => {
      console.log(error)
    });
    this.Router.navigate(['/']);

  }

  onSubmit(){
    console.log(this.myForm.value);
    this.saveHome();
  }

}
