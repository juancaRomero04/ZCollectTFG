import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Producto, ProductoService } from '../services/producto.service';
import { Categoria, CategoriaService } from '../services/categoria.service';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-admin-producto',
  imports: [FormsModule, CommonModule],
  templateUrl: './admin-producto.component.html',
  styleUrl: './admin-producto.component.css'
})
export class AdminProductoComponent implements OnInit {
  producto: Producto = {
    id_producto: '',
    nombre: '',
    descripcion: '',
    precio: 0,
    stock: 0,
    img_url: '',
    categoria: { id_cat: '', nombre: '' }
  };

  categorias: Categoria[] = [];
  esNuevo = true;

  mensajeToast: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private productoService: ProductoService,
    private categoriaService: CategoriaService,
    public router: Router
  ) { }

  async ngOnInit() {
    const id = this.route.snapshot.paramMap.get('id');
    this.categorias = await this.categoriaService.getCategorias();

    if (id) {
      this.esNuevo = false;
      this.producto = await this.productoService.getProductoById(id);
    }
  }

  validarUrlImagen(url: string): boolean {
    const pattern = /\.(jpeg|jpg|gif|png|bmp|webp)$/i;
    return pattern.test(url);
  }

  mostrarToast(mensaje: string) {
    this.mensajeToast = mensaje;
    setTimeout(() => {
      this.mensajeToast = null;
    }, 3000);
  }

  guardar() {
    if (!this.producto.nombre || !this.producto.precio || !this.producto.categoria.id_cat) {
      this.mostrarToast('Completa los campos obligatorios');
      return;
    }

    if (this.producto.precio <= 0) {
      this.mostrarToast('El precio debe ser mayor que 0');
      return;
    }

    if (this.producto.stock < 0) {
      this.mostrarToast('El stock no puede ser negativo');
      return;
    }

    if (this.producto.img_url && !this.validarUrlImagen(this.producto.img_url)) {
      this.mostrarToast('La URL de la imagen no es válida. Debe terminar en .jpg, .jpeg, .png, .gif, .bmp o .webp');
      return;
    }

    if (this.esNuevo) {
      this.productoService.crearProducto(this.producto).then(() => {
        this.mostrarToast('Producto creado');
        setTimeout(() => this.router.navigate(['/catalogo']), 1000);
      });
    } else {
      this.productoService.actualizarProducto(this.producto).then(() => {
        this.mostrarToast('Producto actualizado');
        setTimeout(() => this.router.navigate(['/catalogo']), 1000);
      });
    }
  }

}
