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
import { TaskInfoModalComponent } from './components/task/task-info-modal/task-info-modal.component';
import { FooterComponent } from './components/footer/footer.component';
import { ConfirmationModalComponent } from './components/modals/confirmation-modal/confirmation-modal.component';
import { TaskTableComponent } from './components/tables/task-table/task-table.component';
import { SpentTableComponent } from './components/tables/spent-table/spent-table.component';
import { DebtTableComponent } from './components/tables/debt-table/debt-table.component';
import { MemberProfileComponent } from './components/user/member-profile/member-profile.component';

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
    TaskInfoModalComponent,
    FooterComponent,
    ConfirmationModalComponent,
    TaskTableComponent,
    SpentTableComponent,
    DebtTableComponent,
    MemberProfileComponent,
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
