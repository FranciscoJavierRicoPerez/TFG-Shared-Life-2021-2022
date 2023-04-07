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
  styleUrls: ['./home-create.component.css'],
})
export class HomeCreateComponent implements OnInit {
  token: string;
  home: HomeCreateDTO;
  errorMessage: string = "";
  successMessage: string = "";


  myForm = new FormGroup({
    address: new FormControl('', Validators.required),
    floor: new FormControl('', Validators.required),
    number: new FormControl('', Validators.required),
    city: new FormControl('', Validators.required),
    country: new FormControl('', Validators.required),
    rooms: new FormControl('', Validators.required),
  });

  constructor(
    private HomeService: HomeService,
    private Router: Router,
    private TokenService: TokenService
  ) {}

  ngOnInit(): void {
    if (this.TokenService.getToken()) {
      this.token = this.TokenService.getToken();
    } else {
      this.token = '';
    }
  }

  saveHome() {
    this.errorMessage = "";
    this.successMessage = "";
    this.home = new HomeCreateDTO(
      this.myForm.value.address,
      this.myForm.value.floor,
      this.myForm.value.number,
      this.myForm.value.city,
      this.myForm.value.country,
      this.myForm.value.rooms,
      this.token,
      false
    );
    this.HomeService.createHouse(this.home).subscribe(
      (data) => {
        console.log("OK home created succesfully");
        this.successMessage = "La vivienda se ha creado correctamente, ya puedes empezar a gestionarla.";
      },
      (error) => {
        console.log("ERR home can't be created");
        this.errorMessage = "No se ha podido crear la vivienda, revisa que los datos sean correctos";
      }
    );
    //this.Router.navigate(['/']);
  }

  onSubmit() {
    console.log(this.myForm.value);
    this.saveHome();
  }
}
