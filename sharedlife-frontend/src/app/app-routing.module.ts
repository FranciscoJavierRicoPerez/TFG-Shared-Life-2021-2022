import { LoginComponent } from './components/user/login/login.component';
import { UserProfileComponent } from './components/user/user-profile/user-profile.component';
import { UserRegisterComponent } from './components/user/user-register/user-register.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeCreateComponent } from './components/home/home-create/home-create.component';

const routes: Routes = [
  { path: 'register', component: UserRegisterComponent },
  { path: '', redirectTo: 'register', pathMatch: 'full'}, // al iniciar la aplicacion se redirigira directamente aquí}
  { path: 'user/:id', component: UserProfileComponent},
  { path: 'home/create', component: HomeCreateComponent},
  { path: 'login', component: LoginComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
