import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../services/usuario.service';
import { Router } from '@angular/router';
import { NgFor } from '@angular/common';
@Component({
  selector: 'app-admin-gestion-usuarios',
  imports: [NgFor],
  templateUrl: './admin-gestion-usuarios.component.html',
  styleUrl: './admin-gestion-usuarios.component.css'
})
export class AdminGestionUsuariosComponent implements OnInit{
  usuarios: any[] = [];

  constructor(private usuarioService: UsuarioService, private router: Router) {}

  async ngOnInit() {
    try {
      console.log('[ngOnInit] Cargando usuarios...');
      this.usuarios = await this.usuarioService.obtenerUsuarios();
      console.log('[ngOnInit] Usuarios recibidos:', this.usuarios);
    } catch (error: any) {
      console.error('[ngOnInit] Error al obtener usuarios:', error);
      alert(error.message);
    }
  }

  editarUsuario(usuarioId: string): void {
    console.log('[editarUsuario] Navegando a edición del usuario con ID:', usuarioId);
    this.router.navigate(['/admin/usuarios/editar', usuarioId]);
  }

  async eliminarUsuario(usuarioId: string) {
    if (!confirm('¿Seguro que deseas eliminar este usuario?')) {
      console.log('[eliminarUsuario] Cancelado por el usuario');
      return;
    }

    try {
      console.log('[eliminarUsuario] Eliminando usuario con ID:', usuarioId);
      await this.usuarioService.eliminarUsuario(usuarioId);
      this.usuarios = this.usuarios.filter(u => u.id_user !== usuarioId);
      console.log('[eliminarUsuario] Usuario eliminado correctamente');
    } catch (error: any) {
      console.error('[eliminarUsuario] Error al eliminar usuario:', error);
      alert(error.message);
    }
  }
}
