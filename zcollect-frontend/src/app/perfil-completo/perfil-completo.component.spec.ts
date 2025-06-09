import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PerfilCompletoComponent } from './perfil-completo.component';

describe('PerfilCompletoComponent', () => {
  let component: PerfilCompletoComponent;
  let fixture: ComponentFixture<PerfilCompletoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PerfilCompletoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PerfilCompletoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
