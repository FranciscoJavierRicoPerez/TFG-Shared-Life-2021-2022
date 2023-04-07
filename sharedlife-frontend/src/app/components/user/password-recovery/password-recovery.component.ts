import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-password-recovery',
  templateUrl: './password-recovery.component.html',
  styleUrls: ['./password-recovery.component.css'],
})
export class PasswordRecoveryComponent implements OnInit {
  passwordRecoveryForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
  });

  errorMessage: string = '';
  successMessage: string = '';

  constructor(private AuthService: AuthService) {}

  ngOnInit(): void {}

  onSubmit() {
    console.log(this.passwordRecoveryForm.value.email);
    this.AuthService.updatePassword(
      this.passwordRecoveryForm.value.email
    ).subscribe(
      (data) => {
        console.log('Update succesfully!');
        console.log(data);
        this.successMessage =
          'Contraseña actualizada revisar tu correo electronico, gracias.';
      },
      (err) => {
        console.log('Err updating');
        this.errorMessage =
          'Correo no encontrado para ningun usuario, vuelve a probar.';
      }
    );
  }

  deleteMessages(){
    this.errorMessage = '';
    this.successMessage = '';
  }
}
