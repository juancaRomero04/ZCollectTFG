import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioService } from '../services/usuario.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FormsModule } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-admin-editar-usuarios',
  imports: [FormsModule, ReactiveFormsModule, NgIf],
  templateUrl: './admin-editar-usuarios.component.html',
  styleUrl: './admin-editar-usuarios.component.css'
})
export class AdminEditarUsuariosComponent implements OnInit {
  form: FormGroup;
  usuarioId: string = '';
  mensajeToast: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private usuarioService: UsuarioService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.form = this.fb.group({
      username: ['', [Validators.required, Validators.maxLength(50)]],
      email: ['', [
        Validators.required,
        Validators.email,
        Validators.maxLength(100),
        Validators.pattern(/^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/)
      ]],
      telefono: ['', [
        Validators.required,
        Validators.pattern(/^[0-9]{9}$/)
      ]],
      direccion: ['', [
        Validators.required,
        Validators.maxLength(255)
      ]],
      password: ['', [
        Validators.minLength(8),
        Validators.maxLength(255)
      ]]
    });
  }

  async ngOnInit() {
    this.usuarioId = this.route.snapshot.paramMap.get('id')!;
    try {
      const usuario = await this.usuarioService.obtenerUsuarioPorId(this.usuarioId);
      this.form.patchValue({
        username: usuario.username || '',
        email: usuario.email || '',
        telefono: usuario.telefono || '',
        direccion: usuario.direccion || '',
        password: ''
      });
    } catch (error: any) {
      this.mostrarToast('Error al cargar usuario: ' + error.message);
    }
  }

  mostrarToast(mensaje: string): void {
    this.mensajeToast = mensaje;
    setTimeout(() => {
      this.mensajeToast = null;
    }, 3000);
  }

  async guardar() {
    if (this.form.invalid) {
      this.mostrarToast('Por favor, completa los campos requeridos.');
      return;
    }

    try {
      const formValue = this.form.value;
      const usuarioActualizado = {
        username: formValue.username,
        email: formValue.email,
        telefono: formValue.telefono,
        direccion: formValue.direccion,
        password: formValue.password?.trim() !== '' ? formValue.password : undefined
      };

      await this.usuarioService.actualizarUsuario(this.usuarioId, usuarioActualizado);
      this.mostrarToast('Usuario actualizado correctamente');
      setTimeout(() => this.router.navigate(['/admin/usuarios']), 2000);
    } catch (error: any) {
      this.mostrarToast('Error al actualizar: ' + error.message);
    }
  }

  cancelar() {
    this.router.navigate(['/admin/usuarios']);
  }
}
