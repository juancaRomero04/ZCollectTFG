import { Component, OnInit } from '@angular/core';
import { Categoria, CategoriaService } from '../services/categoria.service';
import { Producto, ProductoService } from '../services/producto.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-catalogo',
  imports: [NgFor, CommonModule, NgIf, FormsModule, RouterModule],
  templateUrl: './catalogo.component.html',
  styleUrl: './catalogo.component.css'
})
export class CatalogoComponent implements OnInit {
  categorias: Categoria[] = [];
  productos: Producto[] = [];

  categoriasConProductos: { categoria: Categoria; productos: Producto[] }[] = [];

  criterioOrden: string = '';
  textoBusqueda: string = '';  // para el input de búsqueda

  constructor(
    private categoriaService: CategoriaService,
    private productoService: ProductoService
  ) { }

  async ngOnInit(): Promise<void> {
    try {
      this.categorias = await this.categoriaService.getCategorias();
      this.productos = await this.productoService.getProductos();

      this.armarCategoriasConProductos();
    } catch (error) {
      console.error('Error cargando datos:', error);
    }
  }

  private armarCategoriasConProductos(): void {
    this.categoriasConProductos = this.categorias.map(cat => {
      const productosFiltrados = this.productos.filter(p => p.categoria?.id_cat === cat.id_cat);
      return {
        categoria: cat,
        productos: productosFiltrados
      };
    });
  }

  ordenarProductos(): void {
    switch (this.criterioOrden) {
      case 'precio_asc':
        this.ordenar((a, b) => a.precio - b.precio);
        break;
      case 'precio_desc':
        this.ordenar((a, b) => b.precio - a.precio);
        break;
      case 'nombre_asc':
        this.ordenar((a, b) => a.nombre.localeCompare(b.nombre));
        break;
      case 'nombre_desc':
        this.ordenar((a, b) => b.nombre.localeCompare(a.nombre));
        break;
      case 'categoria_asc':
        this.categoriasConProductos.sort((a, b) => a.categoria.nombre.localeCompare(b.categoria.nombre));
        break;
      case 'categoria_desc':
        this.categoriasConProductos.sort((a, b) => b.categoria.nombre.localeCompare(a.categoria.nombre));
        break;
      default:
        // Si no hay criterio o es vacio, no ordenar
        break;
    }
  }

  private ordenar(comparador: (a: Producto, b: Producto) => number): void {
    this.categoriasConProductos.forEach(cp => {
      cp.productos.sort(comparador);
    });
  }

  filtrarProductos(): void {
    const texto = this.textoBusqueda.trim().toLowerCase();

    if (!texto) {
      // Si el texto de búsqueda está vacío, mostrar todos los productos agrupados normalmente
      this.armarCategoriasConProductos();
      this.ordenarProductos();
      return;
    }

    // Filtramos productos que coinciden con el texto de búsqueda en su nombre o descripción
    this.categoriasConProductos = this.categorias.map(cat => {
      const productosFiltrados = this.productos.filter(p =>
        p.categoria?.id_cat === cat.id_cat &&
        (p.nombre.toLowerCase().includes(texto) || p.descripcion.toLowerCase().includes(texto))
      );
      return {
        categoria: cat,
        productos: productosFiltrados
      };
    }).filter(cp => cp.productos.length > 0); // eliminamos categorías sin productos tras el filtro

    this.ordenarProductos(); // mantener orden después del filtro
  }


}
