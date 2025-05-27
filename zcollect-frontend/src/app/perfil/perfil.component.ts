import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-perfil',
  imports: [NgIf],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent {
  usuario: any;
  mostrarMensaje = false;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    const datos = localStorage.getItem('usuario');
    if (datos) {
      this.usuario = JSON.parse(datos);
      console.log('Usuario cargado:', this.usuario);
      console.log('ID detectado:', this.usuario.id);
    }
  }




  cerrarSesion(): void {
    this.authService.logout();
    this.mostrarMensaje = true;

    setTimeout(() => {
      this.router.navigate(['/login']);
    }, 2000);
  }
  eliminarUsuario(): void {
    console.log('Intentando eliminar usuario:', this.usuario);
    if (!this.usuario || !this.usuario.id) {
      alert('No se pudo determinar el ID del usuario para eliminar.');
      return;
    }

    if (confirm('¿Estás seguro de que quieres eliminar tu usuario? Esta acción no se puede deshacer.')) {
      this.authService.eliminarUsuario(this.usuario.id).then(response => {
        alert(response.mensaje);
        this.mostrarMensaje = true;
        setTimeout(() => {
          this.cerrarSesion();
        }, 2000);
      }).catch(error => {
        alert('Error al eliminar el usuario: ' + error.message);
      });
    }
  }




}