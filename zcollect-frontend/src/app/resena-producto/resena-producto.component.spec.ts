import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResenaProductoComponent } from './resena-producto.component';

describe('ResenaProductoComponent', () => {
  let component: ResenaProductoComponent;
  let fixture: ComponentFixture<ResenaProductoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ResenaProductoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ResenaProductoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
