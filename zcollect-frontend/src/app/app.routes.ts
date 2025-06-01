import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegistComponent } from './regist/regist.component';
import { PerfilComponent } from './perfil/perfil.component';
import { InfoComponent } from './info/info.component';
import { CatalogoComponent } from './catalogo/catalogo.component';
import { InfoProductoComponent } from './info-producto/info-producto.component';
import { FormularioCompraComponent } from './formulario-compra/formulario-compra.component';
export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'login', component: LoginComponent},
    { path: 'regist', component: RegistComponent},
    { path: 'perfil', component: PerfilComponent},
    { path: 'info', component: InfoComponent},
    { path: 'catalogo', component: CatalogoComponent},
    { path: 'producto/:id', component: InfoProductoComponent },
    { path: 'formulario-compra', component: FormularioCompraComponent }
];
