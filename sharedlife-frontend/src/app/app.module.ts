import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserRegisterComponent } from './components/user/user-register/user-register.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { UserProfileComponent } from './components/user/user-profile/user-profile.component';
import { HomeCreateComponent } from './components/home/home-create/home-create.component';
import { LoginComponent } from './components/user/login/login.component';
import { MenuComponent } from './components/menu/menu.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { interceptorProvider } from './interceptors/home-interceptor.service';
import { HomeInfoPageComponent } from './components/home/home-info-page/home-info-page.component';
import { TaskCreateComponent } from './components/task/task-create/task-create.component';
import { SpentCreateComponent } from './components/spent/spent-create/spent-create.component';


@NgModule({
  declarations: [
    AppComponent,
    UserRegisterComponent,
    UserProfileComponent,
    HomeCreateComponent,
    LoginComponent,
    MenuComponent,
    HomePageComponent,
    HomeInfoPageComponent,
    TaskCreateComponent,
    SpentCreateComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
    FormsModule
  ],
  providers: [interceptorProvider],
  bootstrap: [AppComponent]
})
export class AppModule { }
