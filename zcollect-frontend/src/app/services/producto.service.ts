import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria } from './categoria.service';
export interface Producto {
  id_producto: string;
  nombre: string;
  descripcion: string;
  precio: number;
  img_url: string;
  categoria: Categoria;
  stock: number;
}
@Injectable({
  providedIn: 'root'
})
export class ProductoService {
  private apiUrl = 'http://localhost:8080/productos';

  async getProductos(): Promise<Producto[]> {
    const response = await fetch(this.apiUrl);
    if (!response.ok) {
      throw new Error('Error al obtener productos');
    }
    return response.json();
  }
}
