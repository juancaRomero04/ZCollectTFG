import { Component, OnInit } from '@angular/core';
import { Categoria, CategoriaService } from '../services/categoria.service';
import { Producto, ProductoService } from '../services/producto.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-catalogo',
  imports: [NgFor, CommonModule, NgIf],
  templateUrl: './catalogo.component.html',
  styleUrl: './catalogo.component.css'
})
export class CatalogoComponent implements OnInit {
  categorias: Categoria[] = [];
  productos: Producto[] = [];

  categoriasConProductos: { categoria: Categoria; productos: Producto[] }[] = [];

  constructor(
    private categoriaService: CategoriaService,
    private productoService: ProductoService
  ) { }

  async ngOnInit(): Promise<void> {
    try {
      this.categorias = await this.categoriaService.getCategorias();
      this.productos = await this.productoService.getProductos();

      this.categoriasConProductos = this.categorias.map(cat => {
        const productosFiltrados = this.productos.filter(p => p.categoria?.id_cat === cat.id_cat);
        return {
          categoria: cat,
          productos: productosFiltrados
        };
      });
    } catch (error) {
      console.error('Error cargando datos:', error);
    }
  }


}
