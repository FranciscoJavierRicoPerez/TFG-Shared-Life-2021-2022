import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-footer',
  templateUrl: './footer.component.html',
  styleUrls: ['./footer.component.css']
})
export class FooterComponent implements OnInit {

  info = "Shared Life @2022-2023 v0.1.0 by Francisco Javier Rico Pérez"

  constructor() { }

  ngOnInit(): void {
  }

}
