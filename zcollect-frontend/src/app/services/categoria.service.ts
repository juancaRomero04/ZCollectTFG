import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
export interface Categoria {
  id_cat: string;
  nombre: string;
}
@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  private apiUrl = 'http://localhost:8080/categorias';

  async getCategorias(): Promise<Categoria[]> {
    const response = await fetch(this.apiUrl);
    if (!response.ok) {
      throw new Error('Error al obtener categorías');
    }
    return response.json();
  }
}
