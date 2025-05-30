import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService, Producto } from '../services/producto.service';
import { NgIf, CommonModule } from '@angular/common';
import { CarritoService } from '../services/carrito.service';
@Component({
  selector: 'app-info-producto',
  imports: [NgIf, CommonModule],
  templateUrl: './info-producto.component.html',
  styleUrl: './info-producto.component.css'
})
export class InfoProductoComponent implements OnInit {
  producto: Producto | null = null;

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService,
    private carritoService: CarritoService
  ) {}

  async ngOnInit(): Promise<void> {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      const productos = await this.productoService.getProductos();
      this.producto = productos.find(p => p.id_producto === id) || null;
    }
  }

  async agregarAlCarrito(): Promise<void> {
    try {
      if (this.producto) {
        const usuarioId = this.getUsuarioId();
        await this.carritoService.agregarProducto(usuarioId, this.producto.id_producto, 1);
        alert(`Producto "${this.producto.nombre}" añadido al carrito.`);
        await this.carritoService.cargarCarrito(usuarioId);
      }
    } catch (err) {
      console.error('Error al añadir al carrito', err);
      alert('No se pudo añadir el producto al carrito. Intenta de nuevo.');
    }
  }

  private getUsuarioId(): string {
    const usuario = localStorage.getItem('usuario');
    if (!usuario) throw new Error('Usuario no autenticado');
    return JSON.parse(usuario).id;
  }
}
