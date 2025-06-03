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
  eliminarProducto(id: string): Promise<any> {
    return fetch(`http://localhost:8080/productos/${id}`, {
      method: 'DELETE',
      credentials: 'include'
    }).then(res => {
      if (!res.ok) throw new Error('Error al eliminar');
      return res.json(); // ahora sí hay JSON
    });
  }


  getProductoById(id: string): Promise<Producto> {
    return fetch(`http://localhost:8080/productos/${id}`).then(res => res.json());
  }

  crearProducto(producto: Producto): Promise<any> {
    return fetch(`http://localhost:8080/productos`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(producto),
      credentials: 'include'
    }).then(res => res.json());
  }

  actualizarProducto(producto: Producto): Promise<any> {
    return fetch(`http://localhost:8080/productos/${producto.id_producto}`, {
      method: 'PUT',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(producto),
      credentials: 'include'
    }).then(res => res.json());
  }

}
