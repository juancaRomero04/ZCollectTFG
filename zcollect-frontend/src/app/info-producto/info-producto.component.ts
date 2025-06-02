import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService, Producto } from '../services/producto.service';
import { NgIf, CommonModule } from '@angular/common';
import { CarritoService } from '../services/carrito.service';
import { Router } from '@angular/router';
import { RouterLink } from '@angular/router';
import { NgFor } from '@angular/common';
@Component({
  selector: 'app-info-producto',
  imports: [NgIf, CommonModule, RouterLink],
  templateUrl: './info-producto.component.html',
  styleUrl: './info-producto.component.css'
})
export class InfoProductoComponent implements OnInit {
  producto: Producto | null = null;

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService,
    private carritoService: CarritoService,
    private router2: Router
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
        setTimeout(() => {
          this.router2.navigate(['/catalogo']);
        }, 750);
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
