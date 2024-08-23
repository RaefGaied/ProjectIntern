import { Component, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule } from "@angular/forms";

@Component({
  selector: 'jhi-date-range',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  templateUrl: './date-range.component.html',
  styleUrl: './date-range.component.scss'
})
export class DateRangeComponent {
  dateRangeForm: FormGroup;

  @Output() dateRangeChange = new EventEmitter<{ startDate: string, endDate: string }>();

  constructor(private fb: FormBuilder) {
    this.dateRangeForm = this.fb.group({
      startDate: [''],
      endDate: ['']
    });

    this.dateRangeForm.valueChanges.subscribe(value => {
      this.emitDateRangeChange();
    });
  }

  emitDateRangeChange(): void {
    const { startDate, endDate } = this.dateRangeForm.value;
    this.dateRangeChange.emit({ startDate, endDate });
  }
  updateDateRange(startDate: string, endDate: string): void {
    this.dateRangeChange.emit({ startDate, endDate });
  }
}
