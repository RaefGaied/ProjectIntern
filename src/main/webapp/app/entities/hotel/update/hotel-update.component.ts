import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IUIConfiguration } from 'app/entities/ui-configuration/ui-configuration.model';
import { UIConfigurationService } from 'app/entities/ui-configuration/service/ui-configuration.service';
import { IEmailTemplateConfiguration } from 'app/entities/email-template-configuration/email-template-configuration.model';
import { EmailTemplateConfigurationService } from 'app/entities/email-template-configuration/service/email-template-configuration.service';
import { IAuthentificationConfiguration } from 'app/entities/authentification-configuration/authentification-configuration.model';
import { AuthentificationConfigurationService } from 'app/entities/authentification-configuration/service/authentification-configuration.service';
import { IHotelAdministrateur } from 'app/entities/hotel-administrateur/hotel-administrateur.model';
import { HotelAdministrateurService } from 'app/entities/hotel-administrateur/service/hotel-administrateur.service';
import { HotelService } from '../service/hotel.service';
import { IHotel } from '../hotel.model';
import { HotelFormService, HotelFormGroup } from './hotel-form.service';

@Component({
  standalone: true,
  selector: 'jhi-hotel-update',
  templateUrl: './hotel-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class HotelUpdateComponent implements OnInit {
  isSaving = false;
  hotel: IHotel | null = null;

  uiConfigurationsCollection: IUIConfiguration[] = [];
  emailConfigsCollection: IEmailTemplateConfiguration[] = [];
  authConfigsCollection: IAuthentificationConfiguration[] = [];
  hotelAdministrateursSharedCollection: IHotelAdministrateur[] = [];

  protected hotelService = inject(HotelService);
  protected hotelFormService = inject(HotelFormService);
  protected uIConfigurationService = inject(UIConfigurationService);
  protected emailTemplateConfigurationService = inject(EmailTemplateConfigurationService);
  protected authentificationConfigurationService = inject(AuthentificationConfigurationService);
  protected hotelAdministrateurService = inject(HotelAdministrateurService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: HotelFormGroup = this.hotelFormService.createHotelFormGroup();

  compareUIConfiguration = (o1: IUIConfiguration | null, o2: IUIConfiguration | null): boolean =>
    this.uIConfigurationService.compareUIConfiguration(o1, o2);

  compareEmailTemplateConfiguration = (o1: IEmailTemplateConfiguration | null, o2: IEmailTemplateConfiguration | null): boolean =>
    this.emailTemplateConfigurationService.compareEmailTemplateConfiguration(o1, o2);

  compareAuthentificationConfiguration = (o1: IAuthentificationConfiguration | null, o2: IAuthentificationConfiguration | null): boolean =>
    this.authentificationConfigurationService.compareAuthentificationConfiguration(o1, o2);

  compareHotelAdministrateur = (o1: IHotelAdministrateur | null, o2: IHotelAdministrateur | null): boolean =>
    this.hotelAdministrateurService.compareHotelAdministrateur(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hotel }) => {
      this.hotel = hotel;
      if (hotel) {
        this.updateForm(hotel);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hotel = this.hotelFormService.getHotel(this.editForm);
    if (hotel.id !== null) {
      this.subscribeToSaveResponse(this.hotelService.update(hotel));
    } else {
      this.subscribeToSaveResponse(this.hotelService.create(hotel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHotel>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(hotel: IHotel): void {
    this.hotel = hotel;
    this.hotelFormService.resetForm(this.editForm, hotel);

    this.uiConfigurationsCollection = this.uIConfigurationService.addUIConfigurationToCollectionIfMissing<IUIConfiguration>(
      this.uiConfigurationsCollection,
      hotel.uiConfigurations,
    );
    this.emailConfigsCollection =
      this.emailTemplateConfigurationService.addEmailTemplateConfigurationToCollectionIfMissing<IEmailTemplateConfiguration>(
        this.emailConfigsCollection,
        hotel.emailConfig,
      );
    this.authConfigsCollection =
      this.authentificationConfigurationService.addAuthentificationConfigurationToCollectionIfMissing<IAuthentificationConfiguration>(
        this.authConfigsCollection,
        hotel.authConfig,
      );
    this.hotelAdministrateursSharedCollection =
      this.hotelAdministrateurService.addHotelAdministrateurToCollectionIfMissing<IHotelAdministrateur>(
        this.hotelAdministrateursSharedCollection,
        hotel.hotelAdministrateur,
      );
  }

  protected loadRelationshipsOptions(): void {
    this.uIConfigurationService
      .query({ filter: 'hotel-is-null' })
      .pipe(map((res: HttpResponse<IUIConfiguration[]>) => res.body ?? []))
      .pipe(
        map((uIConfigurations: IUIConfiguration[]) =>
          this.uIConfigurationService.addUIConfigurationToCollectionIfMissing<IUIConfiguration>(
            uIConfigurations,
            this.hotel?.uiConfigurations,
          ),
        ),
      )
      .subscribe((uIConfigurations: IUIConfiguration[]) => (this.uiConfigurationsCollection = uIConfigurations));

    this.emailTemplateConfigurationService
      .query({ filter: 'hotel-is-null' })
      .pipe(map((res: HttpResponse<IEmailTemplateConfiguration[]>) => res.body ?? []))
      .pipe(
        map((emailTemplateConfigurations: IEmailTemplateConfiguration[]) =>
          this.emailTemplateConfigurationService.addEmailTemplateConfigurationToCollectionIfMissing<IEmailTemplateConfiguration>(
            emailTemplateConfigurations,
            this.hotel?.emailConfig,
          ),
        ),
      )
      .subscribe(
        (emailTemplateConfigurations: IEmailTemplateConfiguration[]) => (this.emailConfigsCollection = emailTemplateConfigurations),
      );

    this.authentificationConfigurationService
      .query({ filter: 'hotel-is-null' })
      .pipe(map((res: HttpResponse<IAuthentificationConfiguration[]>) => res.body ?? []))
      .pipe(
        map((authentificationConfigurations: IAuthentificationConfiguration[]) =>
          this.authentificationConfigurationService.addAuthentificationConfigurationToCollectionIfMissing<IAuthentificationConfiguration>(
            authentificationConfigurations,
            this.hotel?.authConfig,
          ),
        ),
      )
      .subscribe(
        (authentificationConfigurations: IAuthentificationConfiguration[]) => (this.authConfigsCollection = authentificationConfigurations),
      );

    this.hotelAdministrateurService
      .query()
      .pipe(map((res: HttpResponse<IHotelAdministrateur[]>) => res.body ?? []))
      .pipe(
        map((hotelAdministrateurs: IHotelAdministrateur[]) =>
          this.hotelAdministrateurService.addHotelAdministrateurToCollectionIfMissing<IHotelAdministrateur>(
            hotelAdministrateurs,
            this.hotel?.hotelAdministrateur,
          ),
        ),
      )
      .subscribe((hotelAdministrateurs: IHotelAdministrateur[]) => (this.hotelAdministrateursSharedCollection = hotelAdministrateurs));
  }
}
