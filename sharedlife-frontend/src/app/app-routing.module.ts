import { SpentCreateComponent } from './components/spent/spent-create/spent-create.component';
import { HomeInfoPageComponent } from './components/home/home-info-page/home-info-page.component';
import { HomePageComponent } from './components/home-page/home-page.component';
import { LoginComponent } from './components/user/login/login.component';
import { UserProfileComponent } from './components/user/user-profile/user-profile.component';
import { UserRegisterComponent } from './components/user/user-register/user-register.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeCreateComponent } from './components/home/home-create/home-create.component';
import { HomeGuardService } from './guards/home-guard.service';
import { TaskCreateComponent } from './components/task/task-create/task-create.component';
import { MemberProfileComponent } from './components/user/member-profile/member-profile.component';
import { HomeChatPageComponent } from './components/chat/home-chat-page/home-chat-page.component';

const routes: Routes = [
  { path: 'register', component: UserRegisterComponent },
  { path: '', redirectTo: 'inicio', pathMatch: 'full' }, // al iniciar la aplicacion se redirigira directamente aquí}
  { path: 'perfil', component: UserProfileComponent },
  {
    path: 'vivienda/nueva',
    component: HomeCreateComponent,
    canActivate: [HomeGuardService],
    data: { expectRole: ['admin'] },
  },
  { path: 'login', component: LoginComponent },
  { path: 'inicio', component: HomePageComponent }, // PAGINA PRINCIPAL DE LA APLICACION
  { path: 'vivienda/:id', component: HomeInfoPageComponent },
  { path: 'vivienda/:id/tareas', component: TaskCreateComponent },
  { path: 'vivienda/:id/gastos', component: SpentCreateComponent },
  { path: 'usuario/:id', component: UserProfileComponent },
  { path: 'usuario/:username/perfil', component: MemberProfileComponent },
  { path: 'vivienda/:id/chat', component: HomeChatPageComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
