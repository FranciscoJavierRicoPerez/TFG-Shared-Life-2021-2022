import { UserRegisterComponent } from './components/user/user-register/user-register.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'register', component: UserRegisterComponent
  },
  {
    path: '', redirectTo: 'register', pathMatch: 'full' // al iniciar la aplicacion se redirigira directamente aquí
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
