import { Component, Output, EventEmitter } from '@angular/core';
import { NgbDropdown, NgbDropdownMenu, NgbDropdownToggle } from "@ng-bootstrap/ng-bootstrap";

@Component({
  selector: 'jhi-guests-rooms-dropdown',
  standalone: true,
  imports: [
    NgbDropdown,
    NgbDropdownMenu,
    NgbDropdownToggle
  ],
  templateUrl: './guests-rooms-dropdown.component.html',
  styleUrl: './guests-rooms-dropdown.component.scss'
})
export class GuestsRoomsDropdownComponent {
  adults: number = 2;
  children: number = 0;
  rooms: number = 1;
  maxRooms: number = 8;

  @Output() guestsRoomsChange = new EventEmitter<{adults: number, children: number, rooms: number}>();

  updateGuests(type: string, delta: number): void {
    switch (type) {
      case 'adults':
        this.adults = Math.max(0, this.adults + delta);
        break;
      case 'children':
        this.children = Math.max(0, this.children + delta);
        break;
      case 'rooms':
        this.rooms = Math.max(1, Math.min(this.maxRooms, this.rooms + delta));
        break;
    }
    // Emit the updated values to the parent component
    this.emitGuestsRoomsChange();
  }

  emitGuestsRoomsChange(): void {
    this.guestsRoomsChange.emit({
      adults: this.adults,
      children: this.children,
      rooms: this.rooms
    });
  }

}
