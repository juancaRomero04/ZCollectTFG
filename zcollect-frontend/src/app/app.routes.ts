import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistComponent } from './regist/regist.component';
import { PerfilComponent } from './perfil/perfil.component';
import { InfoComponent } from './info/info.component';
import { CatalogoComponent } from './catalogo/catalogo.component';
import { InfoProductoComponent } from './info-producto/info-producto.component';
import { FormularioCompraComponent } from './formulario-compra/formulario-compra.component';
import { ResenaProductoComponent } from './resena-producto/resena-producto.component';
import { authGuard } from './guards/auth.guard';
import { AdminProductoComponent } from './admin-producto/admin-producto.component';
import { AdminGestionUsuariosComponent } from './admin-gestion-usuarios/admin-gestion-usuarios.component';
import { AdminEditarUsuariosComponent } from './admin-editar-usuarios/admin-editar-usuarios.component';
export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent},
    { path: 'regist', component: RegistComponent},
    { path: 'perfil', component: PerfilComponent, canActivate: [authGuard]},
    { path: 'info', component: InfoComponent},
    { path: 'catalogo', component: CatalogoComponent},
    { path: 'producto/:id', component: InfoProductoComponent },
    { path: 'formulario-compra', component: FormularioCompraComponent, canActivate: [authGuard] },
    { path: 'comentarios/:id', component: ResenaProductoComponent, canActivate: [authGuard] },
    { path: 'admin/producto', component: AdminProductoComponent },             // Nuevo
    { path: 'admin/producto/:id', component: AdminProductoComponent },         // Editar
    { path: 'admin/usuarios', component: AdminGestionUsuariosComponent },
    { path: 'admin/usuarios/editar/:id', component: AdminEditarUsuariosComponent }


];
