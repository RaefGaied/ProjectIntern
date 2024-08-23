import { Component, inject, signal, OnInit, OnDestroy } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import {range, Subject} from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import {HotelService} from "../entities/hotel/service/hotel.service";
import {NgbDatepickerNavigationSelect} from "@ng-bootstrap/ng-bootstrap/datepicker/datepicker-navigation-select";
import {formatDate} from "@angular/common";
import {DateRangeComponent} from "../date-range/date-range.component";
import {FormsModule} from "@angular/forms";
import {GuestsRoomsDropdownComponent} from "../guests-rooms-dropdown/guests-rooms-dropdown.component";

@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [SharedModule, RouterModule, NgbDatepickerNavigationSelect, DateRangeComponent, FormsModule, GuestsRoomsDropdownComponent],
})
export default class HomeComponent implements OnInit, OnDestroy {
  account = signal<Account | null>(null);

  loading: boolean = false ;

  searchParams = {
    location: '',
    dateRange: {
      startDate: '',
      endDate: ''
    },
    guestsAndRooms: {
      adults: 2,
      children: 0,
      rooms: 1
    }
  };

  private readonly destroy$ = new Subject<void>();

  private accountService = inject(AccountService);
  private router = inject(Router);
  private guestsRoomsDropdownComponent: any;
  constructor(private hotelService: HotelService) {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));
    this.loading = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  // Handle date range change
  onDateRangeChange(event: { startDate: string, endDate: string }): void {
    this.searchParams.dateRange = event;
    console.log('Updated date range:', this.searchParams.dateRange);
  }

  // Handle guests and rooms change
  onGuestsRoomsChange(event: { adults: number, children: number, rooms: number }): void {
    this.searchParams.guestsAndRooms = event;
    console.log('Updated guests and rooms:', this.searchParams.guestsAndRooms);
  }

  // Handle location input
  onLocationChange(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.searchParams.location = input.value;
    console.log('Updated location:', this.searchParams.location);
  }

  // Submit search
  onSearch(): void {
    if (
      this.searchParams.location &&
      this.searchParams.dateRange.startDate &&
      this.searchParams.dateRange.endDate &&
      this.searchParams.guestsAndRooms
    ) {
      console.log('Searching with params:', this.searchParams);
      // Perform search or navigate to the search results
      this.router.navigate(['/api/hotels/'], { queryParams: this.searchParams });
    }
  }

  protected readonly formatDate = formatDate;
  protected range = range;



}
