import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HotelViewDetailsComponent } from './hotel-view-details.component';

describe('HotelViewDetailsComponent', () => {
  let component: HotelViewDetailsComponent;
  let fixture: ComponentFixture<HotelViewDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [HotelViewDetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(HotelViewDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
