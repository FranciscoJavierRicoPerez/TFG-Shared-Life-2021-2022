import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';
import { PasswordUpdateDTO } from 'src/app/models/Password/PasswordUpdateDTO';

@Component({
  selector: 'app-password-update',
  templateUrl: './password-update.component.html',
  styleUrls: ['./password-update.component.css'],
})
export class PasswordUpdateComponent implements OnInit {
  successMessage = '';
  errorMessage = '';

  passwordUpdateForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    actualPassword: new FormControl('', [Validators.required]),
    newPassword: new FormControl('', [Validators.required]),
  });

  constructor(private AuthService: AuthService) {}

  ngOnInit(): void {}

  async onSubmit() {
    console.log('Updating password');
    var passwordUpdateDTO = new PasswordUpdateDTO(
      this.passwordUpdateForm.value.email,
      this.passwordUpdateForm.value.actualPassword,
      this.passwordUpdateForm.value.newPassword
    );
    console.log(passwordUpdateDTO);
    debugger;
    (await this.AuthService.newPassword(passwordUpdateDTO)).subscribe(
      (data) => {
        console.log('OK password update succesfully');
        this.successMessage = 'Contraseña actualizada correctamente.';
      },
      (error) => {
        console.log('ERR password update err');
        this.errorMessage = 'Ha habido un error en la actualzación de la contraseña. Por favor, revise los datos';
      }
    );
  }
}
