import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ResenaService } from '../services/resena.service';
import { NgFor } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
@Component({
  selector: 'app-resena-producto',
  imports: [FormsModule, NgFor, NgIf],
  templateUrl: './resena-producto.component.html',
  styleUrl: './resena-producto.component.css'
})
export class ResenaProductoComponent {
  productoId: string = '';
  resenas: any[] = [];
  comentario: string = '';
  calificacion: number = 5;

  constructor(private route: ActivatedRoute, private resenaService: ResenaService) { }

  async ngOnInit(): Promise<void> {
    this.productoId = this.route.snapshot.paramMap.get('id') || '';
    await this.cargarResenas();
  }

  async cargarResenas() {
    try {
      this.resenas = await this.resenaService.obtenerResenasPorProducto(this.productoId);
    } catch (err) {
      console.error('Error al cargar reseñas:', err);
    }
  }

  async enviarResena() {
    // Validación básica
    if (!this.comentario || this.comentario.trim().length < 3) {
      alert('El comentario debe tener al menos 3 caracteres.');
      return;
    }

    try {
      const usuarioStr = localStorage.getItem('usuario');
      if (!usuarioStr) throw new Error('Usuario no autenticado');
      const usuario = JSON.parse(usuarioStr);
      const userId = usuario.id_user || usuario.id;

      const nuevaResena = {
        usuario: { id_user: userId },
        producto: { id_producto: this.productoId },
        comentario: this.comentario.trim(),
        calificacion: this.calificacion,
        fecha_rese: new Date()
      };

      await this.resenaService.crearResena(nuevaResena);
      this.comentario = '';
      this.calificacion = 5;
      await this.cargarResenas();
    } catch (err) {
      console.error('Error al enviar reseña:', err);
      alert('Error al enviar reseña.');
    }
  }

  // Devuelve un array con N elementos, para usar en *ngFor y mostrar estrellas
  getEstrellas(num: number): any[] {
    return new Array(num);
  }

}
