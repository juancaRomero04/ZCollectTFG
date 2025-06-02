import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class ResenaService {

  private baseUrl = 'http://localhost:8080/reseñas'; // Ajustar si cambia el backend

  // Obtener reseñas por ID de producto
  async obtenerResenasPorProducto(productoId: string) {
    const response = await fetch(`${this.baseUrl}/producto/${productoId}`, {
      credentials: 'include'
    });
    if (!response.ok) throw new Error('Error al obtener reseñas');
    return response.json();
  }

  // Crear nueva reseña
  async crearResena(data: any) {
    const response = await fetch(this.baseUrl, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data),
      credentials: 'include' // para mantener sesión con cookies
    });
    if (!response.ok) throw new Error('Error al crear reseña');
    return response.json();
  }

  // Eliminar reseña por ID
  async eliminarResena(id: string) {
    const response = await fetch(`${this.baseUrl}/${id}`, {
      method: 'DELETE',
      credentials: 'include'
    });
    if (!response.ok) throw new Error('Error al eliminar reseña');
  }
}
