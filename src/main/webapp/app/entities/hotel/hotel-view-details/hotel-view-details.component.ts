import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';
import { IService } from '../../service/service.model';
import SharedModule from '../../../shared/shared.module';
import { DurationPipe, FormatMediumDatePipe, FormatMediumDatetimePipe } from '../../../shared/date';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {ReservationService} from "../../reservation/service/reservation.service";
import dayjs from "dayjs/esm";
import {NewReservation} from "../../reservation/reservation.model";
import { Statut } from 'app/entities/enumerations/statut.model';
import {IUser} from "../../user/user.model";
import {AccountService} from "../../../core/auth/account.service";
import {Account} from "../../../core/auth/account.model";
import {Observable} from "rxjs";

@Component({
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe, CommonModule, FormsModule, ReactiveFormsModule],
  selector: 'jhi-hotel-view-details',
  templateUrl: './hotel-view-details.component.html',
  styleUrls: ['./hotel-view-details.component.scss'],
  standalone: true,
})
export class HotelViewDetailsComponent implements OnInit {

  user: IUser | null=null;
  services: IService[] = [];  // Initialisez services comme un tableau vide
  showReservation = false; // Control visibility of reservation form
  reservationForm: FormGroup;
   rooms = [
    { type: 'Standard Studio', price: 289, description: 'Entire studio with private kitchen and bathroom' },
    { type: 'Superior Studio', price: 362, description: 'Larger studio with city view' },
    { type: 'Deluxe Suite', price: 450, description: 'Spacious suite with separate living area and balcony' },
    { type: 'Executive Suite', price: 620, description: 'Luxurious suite with panoramic city view and premium amenities' },
    { type: 'Family Room', price: 398, description: 'Spacious room with additional beds and family-friendly features' },
    { type: 'Penthouse Suite', price: 850, description: 'Exclusive penthouse with private terrace and top-notch services' },
    { type: 'Junior Suite', price: 375, description: 'Elegant suite with extra space and enhanced comfort' },
    { type: 'Accessible Room', price: 310, description: 'Room designed with accessibility features for comfort and convenience' }
  ];


  reservationSuccess = false; // Track reservation status
  private resourceUrl: any;
  @Input() hotel!: IHotel | undefined;

  constructor(
    private accountService: AccountService,
    private route: ActivatedRoute,
    private hotelService: HotelService,
    private reservationService: ReservationService,
    private fb: FormBuilder
  ) {
    this.reservationForm = this.fb.group({
      roomType: ['', Validators.required],
      numberOfGuests: [1, [Validators.required, Validators.min(1)]],
      startDate: [null, Validators.required],
      endDate: [null, Validators.required]
    });
  }

  ngOnInit(): void {
    // Fetch hotel ID from route
    this.route.params.subscribe(params => {
      const hotelId = params['id']; // Fetch the ID from the URL

      // Now, fetch the hotel details using the ID
      this.hotelService.findWithServices(hotelId).subscribe(response => {
        this.hotel = response;
        console.log('Hotel Data:', this.hotel);

        // If the hotel has services, display them
        if (this.hotel && Array.isArray(this.hotel.services)) {
          this.services = this.hotel.services;
        }
      });
    });

  this.accountService.identity().subscribe(user => {
      if (user && (user as unknown as { id: number }).id) {
        this.user = user as unknown as { id: number }; // Ensure user contains the id
      } else {
        console.error("User is not available or doesn't have an ID");
      }
    });
  }

  showReservationForm(): void {
    this.showReservation = true;
  }

  reserveRoom(): void {
    if (this.reservationForm.valid && this.hotel && this.user) {
      const reservation: NewReservation = {
        id: null,
        dateDebut: dayjs(this.reservationForm.value.startDate),
        dateFin: dayjs(this.reservationForm.value.endDate),
        nombrePersonnes: this.reservationForm.value.numberOfGuests,
        totalPrix: this.rooms.find(room => room.type === this.reservationForm.value.roomType)?.price || 0,
        statutPaiement: Statut.EN_COURS,
        hotel: { id: this.hotel.id },
        user: { id: (this.user as unknown as { id: number }).id }
      };

      this.reservationService.create(reservation).subscribe({
        next: (response) => {
          this.reservationSuccess = true;
          this.showReservation = true;

          const reservationId = response.body?.id; // Correctly access the `id` from the response body
          console.log('Reservation ID:', reservationId);
          console.log('Hotel ID:', this.hotel?.id);
          console.log('User ID:', (this.user as unknown as { id: number }).id);
          console.log('Reservation Data:', reservation);

          if (reservationId) {
            this.reservationService.sendConfirmationEmail(reservationId).subscribe({
              next: () => {
                console.log('Email de confirmation envoyé avec succès');
              },
              error: (err) => {
                console.error('Échec de l\'envoi de l\'email de confirmation :', err);
              }
            });
          }
        },
        error: (err) => {
          console.error('Échec de la réservation :', err);
        }
      });
    }
  }





}







