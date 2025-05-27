import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { Router } from '@angular/router';
@Component({
  selector: 'app-login',
  imports: [NgIf, ReactiveFormsModule, CommonModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = ''; 

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.fb.group({
      username: [''],
      password: ['']
    });
  }

  onSubmit(): void {
    this.authService.login(this.loginForm.value)
      .then(res => {
        console.log('Login exitoso', res);
        this.errorMessage = '';
        this.successMessage = '¡Inicio de sesión exitoso! Redirigiendo...';

        setTimeout(() => {
          this.router.navigate(['/']);
        }, 2000);
      })
      .catch(err => {
        this.successMessage = '';
        this.errorMessage = 'Credenciales inválidas';
      });
  }

}
