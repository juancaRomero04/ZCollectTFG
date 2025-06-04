import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {

  private baseUrl = 'http://localhost:8080/usuarios';

  async obtenerUsuarios(): Promise<any[]> {
    const res = await fetch(this.baseUrl, {
      credentials: 'include'
    });
    if (!res.ok) throw new Error('Error al obtener usuarios');
    return res.json();
  }

  async eliminarUsuario(id: string): Promise<void> {
    const res = await fetch(`${this.baseUrl}/${id}`, {
      method: 'DELETE',
      credentials: 'include'
    });
    if (!res.ok) throw new Error('Error al eliminar usuario');
  }

  async obtenerUsuarioPorId(id: string): Promise<any> {
    const res = await fetch(`${this.baseUrl}/${id}`, {
      credentials: 'include'
    });
    if (!res.ok) throw new Error('Error al obtener usuario');
    return res.json();
  }

  async actualizarUsuario(id: string, data: any): Promise<void> {
    const res = await fetch(`${this.baseUrl}/${id}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
      credentials: 'include'
    });
    if (!res.ok) throw new Error('Error al actualizar usuario');
  }
}
