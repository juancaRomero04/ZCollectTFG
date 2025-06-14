import { Component, OnDestroy, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { Subscription } from 'rxjs';
import { Carrito, CarritoProducto, CarritoService } from '../services/carrito.service';
import { NgFor } from '@angular/common';
import { CurrencyPipe } from '@angular/common';
@Component({
  selector: 'app-perfil',
  imports: [NgIf, NgFor, CurrencyPipe],
  templateUrl: './perfil.component.html',
  styleUrl: './perfil.component.css'
})
export class PerfilComponent implements OnInit, OnDestroy {
  usuario: any;
  mensajeToast: string | null = null;

  carritoItems: CarritoProducto[] = [];
  carritoSub?: Subscription;

  constructor(
    private authService: AuthService,
    private router: Router,
    private carritoService: CarritoService
  ) { }

  ngOnInit(): void {
    const datos = localStorage.getItem('usuario');
    if (datos) {
      this.usuario = JSON.parse(datos);
      this.carritoService.cargarCarrito(this.usuario.id);
      this.carritoSub = this.carritoService.cartItems$.subscribe(items => {
        this.carritoItems = items;
      });
    }
  }

  ngOnDestroy(): void {
    this.carritoSub?.unsubscribe();
  }

  mostrarToast(mensaje: string): void {
    this.mensajeToast = mensaje;
    setTimeout(() => {
      this.mensajeToast = null;
    }, 3000);
  }

  getTotal(): number {
    return this.carritoItems.reduce(
      (total, item) => total + (item.producto.precio * item.cantidadProd),
      0
    );
  }

  cerrarSesion(): void {
    this.authService.logout();
    this.mostrarToast('Sesión cerrada correctamente');
    setTimeout(() => this.router.navigate(['/login']), 2000);
  }

  eliminarUsuario(): void {
    if (!this.usuario?.id) {
      this.mostrarToast('No se pudo determinar el ID del usuario para eliminar.');
      return;
    }

    if (confirm('¿Estás seguro de que quieres eliminar tu usuario? Esta acción no se puede deshacer.')) {
      this.authService.eliminarUsuario(this.usuario.id).then(response => {
        this.mostrarToast(response.mensaje);
        setTimeout(() => this.cerrarSesion(), 2000);
      }).catch(error => {
        this.mostrarToast('Error al eliminar el usuario: ' + error.message);
      });
    }
  }

  async actualizarCantidadProducto(idProducto: string, nuevaCantidad: number) {
    if (!this.usuario?.id) {
      this.mostrarToast('Usuario no identificado');
      return;
    }

    try {
      const carritoActualizado = await this.carritoService.actualizarCantidad(this.usuario.id, idProducto, nuevaCantidad);
      this.carritoItems = carritoActualizado.productos;
    } catch (error: any) {
      this.mostrarToast('Error al actualizar la cantidad: ' + error.message);
    }
  }

  async eliminarProductoDelCarrito(idProducto: string) {
    if (!this.usuario?.id) {
      this.mostrarToast('Usuario no identificado');
      return;
    }

    try {
      const carritoActualizado = await this.carritoService.eliminarProducto(this.usuario.id, idProducto);
      this.carritoItems = carritoActualizado.productos;
    } catch (error: any) {
      this.mostrarToast('Error al eliminar el producto: ' + error.message);
    }
  }

  cambiarCantidad(idProducto: string, nuevaCantidad: number): void {
    const item = this.carritoItems.find(i => i.producto.id_producto === idProducto);
    if (!item) return;

    if (nuevaCantidad > item.producto.stock) {
      this.mostrarToast(`No hay suficiente stock. Stock disponible: ${item.producto.stock}`);
      return;
    }

    if (nuevaCantidad < 1) {
      if (confirm('¿Deseas eliminar este producto del carrito?')) {
        this.eliminarProductoDelCarrito(idProducto);
      }
      return;
    }

    this.actualizarCantidadProducto(idProducto, nuevaCantidad);
  }

  irAFormularioCompra(): void {
    this.router.navigate(['/formulario-compra']);
  }

  irAGestionUsuarios(): void {
    this.router.navigate(['/admin/usuarios']);
  }
  irAPerfil(): void {
    this.router.navigate(['/perfil/completo']);
  }

}