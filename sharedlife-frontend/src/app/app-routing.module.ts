import { HomePageComponent } from './components/home-page/home-page.component';
import { LoginComponent } from './components/user/login/login.component';
import { UserProfileComponent } from './components/user/user-profile/user-profile.component';
import { UserRegisterComponent } from './components/user/user-register/user-register.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeCreateComponent } from './components/home/home-create/home-create.component';
import { HomeGuardService } from './guards/home-guard.service';

const routes: Routes = [
  { path: 'register', component: UserRegisterComponent },
  { path: '', redirectTo: 'inicio', pathMatch: 'full' }, // al iniciar la aplicacion se redirigira directamente aquí}
  { path: 'perfil', component: UserProfileComponent },
  { path: 'home/create', component: HomeCreateComponent, canActivate: [HomeGuardService], data: { expectRole: ['admin'] } },
  { path: 'login', component: LoginComponent },
  { path: 'inicio', component: HomePageComponent } // PAGINA PRINCIPAL DE LA APLICACION
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
