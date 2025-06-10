import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Producto } from './producto.service';

export interface CarritoProducto {
  producto: Producto;
  cantidadProd: number;
  precio: number;
}

export interface Carrito {
  id_carrito: string;
  usuarioId: string;
  fechaC: string;
  productos: CarritoProducto[];
}

@Injectable({
  providedIn: 'root'
})
export class CarritoService {
  private apiUrl = 'http://localhost:8080/carrito';
  private cartItemsSubject = new BehaviorSubject<CarritoProducto[]>([]);
  cartItems$ = this.cartItemsSubject.asObservable();

  constructor() { }

  async obtenerCarritoPorUsuario(idUsuario: string): Promise<Carrito> {
    try {
      const res = await fetch(`${this.apiUrl}/usuario/${idUsuario}`, {
        credentials: 'include'
      });
      if (!res.ok) {
        const errorText = await res.text();
        console.error('Error obtenerCarritoPorUsuario:', errorText);
        throw new Error(errorText || 'Error al obtener el carrito');
      }
      const carrito = await res.json();
      return carrito;
    } catch (error) {
      console.error('Error en obtenerCarritoPorUsuario:', error);
      throw error;
    }
  }

  async cargarCarrito(idUsuario: string): Promise<void> {
    try {
      const carrito = await this.obtenerCarritoPorUsuario(idUsuario);
      this.cartItemsSubject.next(carrito.productos);
    } catch (error) {
      console.error('Error cargando carrito:', error);
    }
  }

  async agregarProducto(idUsuario: string, idProducto: string, cantidad: number): Promise<Carrito> {
    try {
      const url = `${this.apiUrl}/usuario/${idUsuario}/agregar?idProducto=${idProducto}&cantidad=${cantidad}`;

      const res = await fetch(url, {
        method: 'POST',
        credentials: 'include'
      });


      if (!res.ok) {
        const errorText = await res.text();
        console.error('Error respuesta backend agregarProducto:', errorText);
        throw new Error(errorText || 'Error al agregar producto al carrito');
      }

      const carrito: Carrito = await res.json();
      this.cartItemsSubject.next(carrito.productos);
      return carrito;
    } catch (error) {
      console.error('Error en agregarProducto:', error);
      throw error;
    }
  }

  async eliminarProducto(idUsuario: string, idProducto: string): Promise<Carrito> {
    try {
      const url = `${this.apiUrl}/usuario/${idUsuario}/producto/${idProducto}`;

      const res = await fetch(url, {
        method: 'DELETE',
        credentials: 'include'
      });


      if (!res.ok) {
        const errorText = await res.text();
        console.error('Error respuesta backend eliminarProducto:', errorText);
        throw new Error(errorText || 'Error al eliminar producto del carrito');
      }

      const carrito: Carrito = await res.json();
      this.cartItemsSubject.next(carrito.productos);
      return carrito;
    } catch (error) {
      console.error('Error en eliminarProducto:', error);
      throw error;
    }
  }

  async vaciarCarrito(idUsuario: string): Promise<void> {
    try {
      const url = `${this.apiUrl}/vaciar/${idUsuario}`;

      const res = await fetch(url, {
        method: 'DELETE',
        credentials: 'include'
      });


      if (!res.ok) {
        const errorText = await res.text();
        console.error('Error respuesta backend vaciarCarrito:', errorText);
        throw new Error(errorText || 'Error al vaciar carrito');
      }

      this.cartItemsSubject.next([]);
    } catch (error) {
      console.error('Error en vaciarCarrito:', error);
      throw error;
    }
  }

  async actualizarCantidad(idUsuario: string, idProducto: string, cantidad: number): Promise<Carrito> {
    try {
      const url = `${this.apiUrl}/actualizar/${idUsuario}/${idProducto}?cantidad=${cantidad}`;

      const res = await fetch(url, {
        method: 'PUT',
        credentials: 'include'
      });


      if (!res.ok) {
        const errorText = await res.text();
        console.error('Error respuesta backend actualizarCantidad:', errorText);
        throw new Error(errorText || 'Error al actualizar cantidad');
      }

      const carrito: Carrito = await res.json();
      this.cartItemsSubject.next(carrito.productos);
      return carrito;
    } catch (error) {
      console.error('Error en actualizarCantidad:', error);
      throw error;
    }
  }

}
