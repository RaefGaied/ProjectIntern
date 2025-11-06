import {Component, inject, signal, OnInit, OnDestroy, Input, Output, EventEmitter} from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import {range, Subject} from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import SharedModule from 'app/shared/shared.module';
import { AccountService } from 'app/core/auth/account.service';
import { Account } from 'app/core/auth/account.model';
import {HotelService} from "../entities/hotel/service/hotel.service"
import { IHotel, NewHotel } from 'app/entities/hotel/hotel.model';
import { IUIConfiguration } from 'app/entities/ui-configuration/ui-configuration.model';
import {NgbDatepickerNavigationSelect} from "@ng-bootstrap/ng-bootstrap/datepicker/datepicker-navigation-select";
import {formatDate} from "@angular/common";
import {DateRangeComponent} from "../date-range/date-range.component";
import {FormsModule} from "@angular/forms";
import {GuestsRoomsDropdownComponent} from "../guests-rooms-dropdown/guests-rooms-dropdown.component";
import {HttpClient} from "@angular/common/http";
import uIConfigurationResolve from "../entities/ui-configuration/route/ui-configuration-routing-resolve.service";
import HasAnyAuthorityDirective from "../shared/auth/has-any-authority.directive";
import {UIConfigurationService} from "../entities/ui-configuration/service/ui-configuration.service";
import { HotelViewDetailsComponent } from '../entities/hotel/hotel-view-details/hotel-view-details.component';



@Component({
  standalone: true,
  selector: 'jhi-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  imports: [SharedModule, RouterModule, NgbDatepickerNavigationSelect, DateRangeComponent, FormsModule, GuestsRoomsDropdownComponent, HasAnyAuthorityDirective, HotelViewDetailsComponent]
})
export default class HomeComponent implements OnInit, OnDestroy {
  account = signal<Account | null>(null);

  loading: boolean = false ;

  hotels: NewHotel[] = [];

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
  selectedHotelChange = new EventEmitter<NewHotel>();
  @Input() hotel: null = null;
  private accountService = inject(AccountService);
  private router = inject(Router);
  private guestsRoomsDropdownComponent: any;
  private http = inject(HttpClient);
  private hotelService = inject(HotelService);
  private uiConfigurationService = inject(UIConfigurationService);
   @Output()logoUrl: string | null | undefined ;
   @Output()bannerUrl: string | null | undefined ;
   hotelSearchResult = new EventEmitter<NewHotel[]>();

   private selectedHotel: IHotel | undefined ;



  constructor() {}

  ngOnInit(): void {
    this.accountService
      .getAuthenticationState()
      .pipe(takeUntil(this.destroy$))
      .subscribe(account => this.account.set(account));
    this.loading = true;
    this.loadUIConfiguration();
  }

  loadUIConfiguration(): void {
    this.uiConfigurationService.find(2).subscribe({
      next: response => {
        const uiConfig = response.body;
        if (uiConfig) {
          this.setUrls(uiConfig);  // Call setUrls method to set logo and banner
        }
      },
      error: () => {
        console.error('Failed to load UI configuration');
      }
    });
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
  setUrls(uiConfiguration: IUIConfiguration | null) {
    this.logoUrl = uiConfiguration?.logo ? `/content/images/${encodeURIComponent(uiConfiguration.logo)}` : null;
    this.bannerUrl = uiConfiguration?.banner ? `/content/images/${encodeURIComponent(uiConfiguration.banner)}` : null;
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
  searchHotels(): void {
    this.loading = true;
    const params = {
      location: this.searchParams.location,
      checkInDate: this.searchParams.dateRange.startDate,
      checkOutDate: this.searchParams.dateRange.endDate,
      adults: this.searchParams.guestsAndRooms.adults.toString(),
      children: this.searchParams.guestsAndRooms.children.toString(),
      rooms: this.searchParams.guestsAndRooms.rooms.toString()
    };

    this.http.get<NewHotel[]>('/api/hotels/search', { params }).subscribe(
      (response: NewHotel[]) => {
        this.hotels = response;
        this.hotelSearchResult.emit(this.hotels); // Emit search results
        this.loading = false;

      },
      (error: any) => {
        console.error('Search failed', error);
        this.loading = false;
      }
    );
  }
  protected readonly formatDate = formatDate;
  protected range = range;



}
