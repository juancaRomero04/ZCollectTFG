import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/usuarios';
  private loggedIn = new BehaviorSubject<boolean>(this.hasToken());

  constructor() { }

  // Observable para el navbar
  get isLoggedIn() {
    return this.loggedIn.asObservable();
  }

  // Verifica si hay sesión en localStorage
  private hasToken(): boolean {
    return !!localStorage.getItem('loggedIn');
  }

  login(credentials: { username: string; password: string }): Promise<any> {
    return fetch(`${this.baseUrl}/login`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(credentials),
      credentials: 'include'
    }).then(async res => {
      if (!res.ok) throw new Error('Credenciales inválidas');
      const userData = await res.json();
      localStorage.setItem('loggedIn', 'true');
      localStorage.setItem('usuario', JSON.stringify(userData));

      this.loggedIn.next(true);
      return userData;
    });
  }



  logout(): void {
    localStorage.removeItem('loggedIn');
    localStorage.removeItem('usuario'); // limpiar usuario guardado
    this.loggedIn.next(false);
  }
  isLogged(): boolean {
    return this.loggedIn.value;
  }


  // Registro
  register(data: any): Promise<any> {
    return fetch(`${this.baseUrl}/registro`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    }).then(async res => {
      const json = await res.json();
      if (!res.ok) {
        if (typeof json === 'object' && !Array.isArray(json)) {
          throw json; // errores por campo
        }
        throw new Error(json?.error || 'Error en registro');
      }
      return json;
    });
  }
  eliminarUsuario(id_user: string): Promise<any> {
    return fetch(`${this.baseUrl}/${id_user}`, {
      method: 'DELETE',
      credentials: 'include',
    }).then(async res => {
      if (!res.ok) {
        try {
          const error = await res.json();
          throw new Error(error.error || 'Error desconocido');
        } catch {
          throw new Error('Error desconocido al eliminar usuario');
        }
      }
      return await res.json();
    });
  }
  getUsuario(): any {
    const user = localStorage.getItem('usuario');
    return user ? JSON.parse(user) : null;
  }




}
