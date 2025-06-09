import { Component, OnInit } from '@angular/core';
import { Categoria, CategoriaService } from '../services/categoria.service';
import { Producto, ProductoService } from '../services/producto.service';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { NgIf } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';
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

  criterioOrden = '';
  textoBusqueda = '';
  filtroCategoria = '';
  precioMinAbsoluto = 0;
  precioMaxAbsoluto = 1000;
  precioMin = 0;
  precioMax = 1000;

  esAdmin = false;

  constructor(
    private categoriaService: CategoriaService,
    private productoService: ProductoService,
    private authService: AuthService,
    private router: Router
  ) {}

  async ngOnInit(): Promise<void> {
    try {
      const usuario = this.authService.getUsuario();
      this.esAdmin = usuario?.roles?.includes('ROLE_ADMIN');

      this.categorias = await this.categoriaService.getCategorias();
      this.productos = await this.productoService.getProductos();

      this.establecerRangoPrecios();
      this.aplicarFiltros();
    } catch (error) {
      console.error('Error cargando datos:', error);
    }
  }

  establecerRangoPrecios(): void {
    const precios = this.productos.map(p => p.precio);
    this.precioMinAbsoluto = Math.min(...precios);
    this.precioMaxAbsoluto = Math.max(...precios);
    this.precioMin = this.precioMinAbsoluto;
    this.precioMax = this.precioMaxAbsoluto;
  }

  aplicarFiltros(): void {
  const texto = this.textoBusqueda.trim().toLowerCase();

  const productosFiltrados = this.productos.filter(p => {
    const coincideCategoria = this.filtroCategoria ? p.categoria?.id_cat === this.filtroCategoria : true;
    const coincideBusqueda =
      p.nombre.toLowerCase().includes(texto) || p.descripcion.toLowerCase().includes(texto);
    const coincidePrecioMin = p.precio >= this.precioMin;
    const coincidePrecioMax = p.precio <= this.precioMax;

    // NUEVO: mostrar solo productos con stock > 0 si NO es admin
    const coincideStock = this.esAdmin ? true : (p.stock > 0);

    return coincideCategoria && coincideBusqueda && coincidePrecioMin && coincidePrecioMax && coincideStock;
  });

  this.categoriasConProductos = this.categorias.map(cat => {
    const productosCat = productosFiltrados.filter(p => p.categoria?.id_cat === cat.id_cat);
    return {
      categoria: cat,
      productos: productosCat
    };
  }).filter(cp => cp.productos.length > 0);

  this.ordenarProductos();
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
        break;
    }
  }

  private ordenar(comparador: (a: Producto, b: Producto) => number): void {
    this.categoriasConProductos.forEach(cp => {
      cp.productos.sort(comparador);
    });
  }

  eliminarProducto(id: string): void {
    if (confirm('¿Estás seguro de eliminar este producto?')) {
      this.productoService.eliminarProducto(id).then(() => {
        alert('Producto eliminado');
        this.ngOnInit();
      }).catch(err => {
        console.error(err);
        alert('Error al eliminar el producto');
      });
    }
  }

  editarProducto(producto: Producto): void {
    this.router.navigate(['/admin/producto', producto.id_producto]);
  }

  anadirProducto(): void {
    this.router.navigate(['/admin/producto']);
  }


}
