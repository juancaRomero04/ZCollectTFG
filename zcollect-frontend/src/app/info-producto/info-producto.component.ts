import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ProductoService, Producto } from '../services/producto.service';
import { NgIf, CommonModule } from '@angular/common';
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
    private productoService: ProductoService
  ) { }

  async ngOnInit(): Promise<void> {
    const id = this.route.snapshot.paramMap.get('id');
    const productos = await this.productoService.getProductos();
    this.producto = productos.find(p => p.id_producto === id) || null;
  }
}
