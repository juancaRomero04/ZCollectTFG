import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { NgIf } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { NgFor } from '@angular/common';
import { CommonModule } from '@angular/common';
@Component({
  selector: 'app-perfil-completo',
  imports: [NgIf, RouterModule, NgFor, CommonModule],
  templateUrl: './perfil-completo.component.html',
  styleUrl: './perfil-completo.component.css'
})
export class PerfilCompletoComponent implements OnInit {
  usuario: any = null;
  pedidos: any[] = [];
  errorMsg: string = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const usuarioGuardado = this.authService.getUsuario();
    if (usuarioGuardado?.id) {
      this.cargarUsuario(usuarioGuardado.id);
      this.cargarPedidos(usuarioGuardado.id);
    } else {
      this.errorMsg = 'No hay usuario autenticado';
      console.warn(this.errorMsg);
    }
  }

  cargarUsuario(idUsuario: string): void {
    fetch(`http://localhost:8080/usuarios/${idUsuario}`, {
      method: 'GET',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => {
        if (!res.ok) {
          console.error(`Error HTTP al obtener usuario: ${res.status} ${res.statusText}`);
          throw new Error('Error al obtener el usuario');
        }
        return res.json();
      })
      .then(data => {
        this.usuario = data;
      })
      .catch(err => {
        this.errorMsg = 'Error cargando usuario: ' + err.message;
        console.error(this.errorMsg);
      });
  }

  cargarPedidos(idUsuario: string): void {
    fetch(`http://localhost:8080/pedidos/${idUsuario}/pedidos`, {
      method: 'GET',
      credentials: 'include',
      headers: { 'Content-Type': 'application/json' }
    })
      .then(res => {
        if (!res.ok) {
          console.error(`Error HTTP al obtener pedidos: ${res.status} ${res.statusText}`);
          throw new Error('Error al obtener pedidos');
        }
        return res.json();
      })
      .then(data => {
        this.pedidos = data;
      })
      .catch(err => {
        this.errorMsg = 'Error cargando pedidos: ' + err.message;
      });
  }

}
