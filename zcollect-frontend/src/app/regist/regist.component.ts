import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AuthService } from '../services/auth.service';
import { NgIf } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-regist',
  imports: [NgIf, ReactiveFormsModule, CommonModule],
  templateUrl: './regist.component.html',
  styleUrl: './regist.component.css'
})
export class RegistComponent {
  registerForm: FormGroup;
  errorMessage: string = '';
  successMessage: string = '';
  fieldErrors: { [key: string]: string } = {}; // errores por campo

  constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
    this.registerForm = this.fb.group({
      username: [''],
      password: [''],
      email: [''],
      telefono: [''],
      direccion: ['']
    });
  }

  onSubmit(): void {
    this.fieldErrors = {}; // limpia errores previos

    if (this.registerForm.invalid) {
      this.errorMessage = 'Por favor completa todos los campos requeridos correctamente.';
      this.successMessage = '';
      return;
    }

    this.authService.register(this.registerForm.value)
      .then(res => {
        this.successMessage = 'Registro exitoso. Redirigiendo al inicio de sesión...';
        this.errorMessage = '';
        this.fieldErrors = {};
        this.registerForm.reset();

        // Espera 2 segundos y redirige a /login
        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 2000);
      })
      .catch(err => {
        this.successMessage = '';

        // Si es un objeto con errores de validación
        if (typeof err === 'object' && err !== null && !(err instanceof Error)) {
          this.fieldErrors = err;
          this.errorMessage = 'Hay errores en el formulario.';
        } else if (err instanceof Error) {
          this.errorMessage = err.message;
        } else {
          this.errorMessage = 'Error desconocido al registrar usuario.';
        }
      });
  }

  getError(field: string): string {
    return this.fieldErrors[field] || '';
  }
}
