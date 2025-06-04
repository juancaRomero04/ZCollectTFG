import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdminEditarUsuariosComponent } from './admin-editar-usuarios.component';

describe('AdminEditarUsuariosComponent', () => {
  let component: AdminEditarUsuariosComponent;
  let fixture: ComponentFixture<AdminEditarUsuariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdminEditarUsuariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdminEditarUsuariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
