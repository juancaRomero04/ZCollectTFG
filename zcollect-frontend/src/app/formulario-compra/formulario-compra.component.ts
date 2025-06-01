import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgIf } from '@angular/common';
import { NgFor } from '@angular/common';
import { FormsModule, NgModel } from '@angular/forms';
import { NgClass } from '@angular/common';
@Component({
  selector: 'app-formulario-compra',
  imports: [NgIf, FormsModule, NgClass, NgFor],
  templateUrl: './formulario-compra.component.html',
  styleUrl: './formulario-compra.component.css'
})
export class FormularioCompraComponent {
  nombre = '';
  numeroTarjeta = '';
  cvv = '';
  vencimiento = '';
  tipoTarjeta = '';
  mensaje = '';
  cargando = false;

  // Guardamos todos los errores por campo aquí
  erroresCampos: { [campo: string]: string[] } = {};

  constructor(private router: Router) {}

  async enviarFormulario() {
    this.mensaje = '';
    this.cargando = true;
    this.erroresCampos = {}; // resetear errores previos

    const tarjetaDTO = {
      nombreTitular: this.nombre,
      numeroTarjeta: this.numeroTarjeta,
      cvv: this.cvv,
      fechaExpiracion: this.vencimiento,
      tipoTarjeta: this.tipoTarjeta,
    };

    try {
      const response = await fetch('http://localhost:8080/pedidos/crear', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(tarjetaDTO),
        credentials: 'include',
      });

      if (response.ok) {
        this.mensaje = 'Perfecto Compra realizada con éxito.';
        setTimeout(() => {
          this.router.navigate(['/perfil']);
        }, 2000);
      } else {
        const errorText = await response.text();

        try {
          const errorObj = JSON.parse(errorText);

          // Guardamos todos los errores, siempre como arrays, para mostrar todos
          const camposOrdenados = [
            'nombreTitular',
            'numeroTarjeta',
            'cvv',
            'fechaExpiracion',
            'tipoTarjeta',
          ];

          for (const campo of camposOrdenados) {
            if (errorObj[campo]) {
              let errores = errorObj[campo];
              if (!Array.isArray(errores)) {
                errores = [String(errores)];
              }
              this.erroresCampos[campo] = errores;
            }
          }

          this.mensaje = 'Por favor corrige los errores en el formulario.';
        } catch {
          this.mensaje = `Error: ${errorText}`;
        }
      }
    } catch (error: any) {
      this.mensaje = `Error en la conexión: ${error.message || error}`;
    } finally {
      this.cargando = false;
    }
  }
}
