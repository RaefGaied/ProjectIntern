import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { IUIConfiguration } from 'app/entities/ui-configuration/ui-configuration.model';
import { UIConfigurationService } from 'app/entities/ui-configuration/service/ui-configuration.service';
import { IEmailTemplateConfiguration } from 'app/entities/email-template-configuration/email-template-configuration.model';
import { EmailTemplateConfigurationService } from 'app/entities/email-template-configuration/service/email-template-configuration.service';
import { IAuthentificationConfiguration } from 'app/entities/authentification-configuration/authentification-configuration.model';
import { AuthentificationConfigurationService } from 'app/entities/authentification-configuration/service/authentification-configuration.service';
import { IHotelAdministrateur } from 'app/entities/hotel-administrateur/hotel-administrateur.model';
import { HotelAdministrateurService } from 'app/entities/hotel-administrateur/service/hotel-administrateur.service';
import { IHotel } from '../hotel.model';
import { HotelService } from '../service/hotel.service';
import { HotelFormService } from './hotel-form.service';

import { HotelUpdateComponent } from './hotel-update.component';

describe('Hotel Management Update Component', () => {
  let comp: HotelUpdateComponent;
  let fixture: ComponentFixture<HotelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let hotelFormService: HotelFormService;
  let hotelService: HotelService;
  let uIConfigurationService: UIConfigurationService;
  let emailTemplateConfigurationService: EmailTemplateConfigurationService;
  let authentificationConfigurationService: AuthentificationConfigurationService;
  let hotelAdministrateurService: HotelAdministrateurService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HotelUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(HotelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HotelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    hotelFormService = TestBed.inject(HotelFormService);
    hotelService = TestBed.inject(HotelService);
    uIConfigurationService = TestBed.inject(UIConfigurationService);
    emailTemplateConfigurationService = TestBed.inject(EmailTemplateConfigurationService);
    authentificationConfigurationService = TestBed.inject(AuthentificationConfigurationService);
    hotelAdministrateurService = TestBed.inject(HotelAdministrateurService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call uiConfigurations query and add missing value', () => {
      const hotel: IHotel = { id: 456 };
      const uiConfigurations: IUIConfiguration = { id: 11226 };
      hotel.uiConfigurations = uiConfigurations;

      const uiConfigurationsCollection: IUIConfiguration[] = [{ id: 10889 }];
      jest.spyOn(uIConfigurationService, 'query').mockReturnValue(of(new HttpResponse({ body: uiConfigurationsCollection })));
      const expectedCollection: IUIConfiguration[] = [uiConfigurations, ...uiConfigurationsCollection];
      jest.spyOn(uIConfigurationService, 'addUIConfigurationToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      expect(uIConfigurationService.query).toHaveBeenCalled();
      expect(uIConfigurationService.addUIConfigurationToCollectionIfMissing).toHaveBeenCalledWith(
        uiConfigurationsCollection,
        uiConfigurations,
      );
      expect(comp.uiConfigurationsCollection).toEqual(expectedCollection);
    });

    it('Should call emailConfig query and add missing value', () => {
      const hotel: IHotel = { id: 456 };
      const emailConfig: IEmailTemplateConfiguration = { id: 18311 };
      hotel.emailConfig = emailConfig;

      const emailConfigCollection: IEmailTemplateConfiguration[] = [{ id: 682 }];
      jest.spyOn(emailTemplateConfigurationService, 'query').mockReturnValue(of(new HttpResponse({ body: emailConfigCollection })));
      const expectedCollection: IEmailTemplateConfiguration[] = [emailConfig, ...emailConfigCollection];
      jest
        .spyOn(emailTemplateConfigurationService, 'addEmailTemplateConfigurationToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      expect(emailTemplateConfigurationService.query).toHaveBeenCalled();
      expect(emailTemplateConfigurationService.addEmailTemplateConfigurationToCollectionIfMissing).toHaveBeenCalledWith(
        emailConfigCollection,
        emailConfig,
      );
      expect(comp.emailConfigsCollection).toEqual(expectedCollection);
    });

    it('Should call authConfig query and add missing value', () => {
      const hotel: IHotel = { id: 456 };
      const authConfig: IAuthentificationConfiguration = { id: 2578 };
      hotel.authConfig = authConfig;

      const authConfigCollection: IAuthentificationConfiguration[] = [{ id: 6594 }];
      jest.spyOn(authentificationConfigurationService, 'query').mockReturnValue(of(new HttpResponse({ body: authConfigCollection })));
      const expectedCollection: IAuthentificationConfiguration[] = [authConfig, ...authConfigCollection];
      jest
        .spyOn(authentificationConfigurationService, 'addAuthentificationConfigurationToCollectionIfMissing')
        .mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      expect(authentificationConfigurationService.query).toHaveBeenCalled();
      expect(authentificationConfigurationService.addAuthentificationConfigurationToCollectionIfMissing).toHaveBeenCalledWith(
        authConfigCollection,
        authConfig,
      );
      expect(comp.authConfigsCollection).toEqual(expectedCollection);
    });

    it('Should call HotelAdministrateur query and add missing value', () => {
      const hotel: IHotel = { id: 456 };
      const hotelAdministrateur: IHotelAdministrateur = { id: 11134 };
      hotel.hotelAdministrateur = hotelAdministrateur;

      const hotelAdministrateurCollection: IHotelAdministrateur[] = [{ id: 4362 }];
      jest.spyOn(hotelAdministrateurService, 'query').mockReturnValue(of(new HttpResponse({ body: hotelAdministrateurCollection })));
      const additionalHotelAdministrateurs = [hotelAdministrateur];
      const expectedCollection: IHotelAdministrateur[] = [...additionalHotelAdministrateurs, ...hotelAdministrateurCollection];
      jest.spyOn(hotelAdministrateurService, 'addHotelAdministrateurToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      expect(hotelAdministrateurService.query).toHaveBeenCalled();
      expect(hotelAdministrateurService.addHotelAdministrateurToCollectionIfMissing).toHaveBeenCalledWith(
        hotelAdministrateurCollection,
        ...additionalHotelAdministrateurs.map(expect.objectContaining),
      );
      expect(comp.hotelAdministrateursSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const hotel: IHotel = { id: 456 };
      const uiConfigurations: IUIConfiguration = { id: 31784 };
      hotel.uiConfigurations = uiConfigurations;
      const emailConfig: IEmailTemplateConfiguration = { id: 3304 };
      hotel.emailConfig = emailConfig;
      const authConfig: IAuthentificationConfiguration = { id: 28250 };
      hotel.authConfig = authConfig;
      const hotelAdministrateur: IHotelAdministrateur = { id: 16155 };
      hotel.hotelAdministrateur = hotelAdministrateur;

      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      expect(comp.uiConfigurationsCollection).toContain(uiConfigurations);
      expect(comp.emailConfigsCollection).toContain(emailConfig);
      expect(comp.authConfigsCollection).toContain(authConfig);
      expect(comp.hotelAdministrateursSharedCollection).toContain(hotelAdministrateur);
      expect(comp.hotel).toEqual(hotel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotel>>();
      const hotel = { id: 123 };
      jest.spyOn(hotelFormService, 'getHotel').mockReturnValue(hotel);
      jest.spyOn(hotelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotel }));
      saveSubject.complete();

      // THEN
      expect(hotelFormService.getHotel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(hotelService.update).toHaveBeenCalledWith(expect.objectContaining(hotel));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotel>>();
      const hotel = { id: 123 };
      jest.spyOn(hotelFormService, 'getHotel').mockReturnValue({ id: null });
      jest.spyOn(hotelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: hotel }));
      saveSubject.complete();

      // THEN
      expect(hotelFormService.getHotel).toHaveBeenCalled();
      expect(hotelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IHotel>>();
      const hotel = { id: 123 };
      jest.spyOn(hotelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ hotel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(hotelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareUIConfiguration', () => {
      it('Should forward to uIConfigurationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(uIConfigurationService, 'compareUIConfiguration');
        comp.compareUIConfiguration(entity, entity2);
        expect(uIConfigurationService.compareUIConfiguration).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareEmailTemplateConfiguration', () => {
      it('Should forward to emailTemplateConfigurationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(emailTemplateConfigurationService, 'compareEmailTemplateConfiguration');
        comp.compareEmailTemplateConfiguration(entity, entity2);
        expect(emailTemplateConfigurationService.compareEmailTemplateConfiguration).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareAuthentificationConfiguration', () => {
      it('Should forward to authentificationConfigurationService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(authentificationConfigurationService, 'compareAuthentificationConfiguration');
        comp.compareAuthentificationConfiguration(entity, entity2);
        expect(authentificationConfigurationService.compareAuthentificationConfiguration).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareHotelAdministrateur', () => {
      it('Should forward to hotelAdministrateurService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(hotelAdministrateurService, 'compareHotelAdministrateur');
        comp.compareHotelAdministrateur(entity, entity2);
        expect(hotelAdministrateurService.compareHotelAdministrateur).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
