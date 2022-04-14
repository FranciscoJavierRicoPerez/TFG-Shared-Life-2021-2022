import { Router } from '@angular/router';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HomeService } from './../../../services/home/home.service';
import { Component, OnInit } from '@angular/core';
import { Home } from 'src/app/models/home/home';

@Component({
  selector: 'app-home-create',
  templateUrl: './home-create.component.html',
  styleUrls: ['./home-create.component.css']
})
export class HomeCreateComponent implements OnInit {

  home: Home = new Home();

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
    private Router: Router) {}

  ngOnInit(): void {
  }

  saveHome(){
    this.HomeService.createHouse(this.myForm.value).subscribe(
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
