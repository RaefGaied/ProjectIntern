import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { AuthentificationConfigurationService } from '../service/authentification-configuration.service';
import { IAuthentificationConfiguration } from '../authentification-configuration.model';
import { AuthentificationConfigurationFormService } from './authentification-configuration-form.service';

import { AuthentificationConfigurationUpdateComponent } from './authentification-configuration-update.component';

describe('AuthentificationConfiguration Management Update Component', () => {
  let comp: AuthentificationConfigurationUpdateComponent;
  let fixture: ComponentFixture<AuthentificationConfigurationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let authentificationConfigurationFormService: AuthentificationConfigurationFormService;
  let authentificationConfigurationService: AuthentificationConfigurationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AuthentificationConfigurationUpdateComponent],
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
      .overrideTemplate(AuthentificationConfigurationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuthentificationConfigurationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    authentificationConfigurationFormService = TestBed.inject(AuthentificationConfigurationFormService);
    authentificationConfigurationService = TestBed.inject(AuthentificationConfigurationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const authentificationConfiguration: IAuthentificationConfiguration = { id: 456 };

      activatedRoute.data = of({ authentificationConfiguration });
      comp.ngOnInit();

      expect(comp.authentificationConfiguration).toEqual(authentificationConfiguration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationConfiguration>>();
      const authentificationConfiguration = { id: 123 };
      jest
        .spyOn(authentificationConfigurationFormService, 'getAuthentificationConfiguration')
        .mockReturnValue(authentificationConfiguration);
      jest.spyOn(authentificationConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authentificationConfiguration }));
      saveSubject.complete();

      // THEN
      expect(authentificationConfigurationFormService.getAuthentificationConfiguration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(authentificationConfigurationService.update).toHaveBeenCalledWith(expect.objectContaining(authentificationConfiguration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationConfiguration>>();
      const authentificationConfiguration = { id: 123 };
      jest.spyOn(authentificationConfigurationFormService, 'getAuthentificationConfiguration').mockReturnValue({ id: null });
      jest.spyOn(authentificationConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationConfiguration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authentificationConfiguration }));
      saveSubject.complete();

      // THEN
      expect(authentificationConfigurationFormService.getAuthentificationConfiguration).toHaveBeenCalled();
      expect(authentificationConfigurationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAuthentificationConfiguration>>();
      const authentificationConfiguration = { id: 123 };
      jest.spyOn(authentificationConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authentificationConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(authentificationConfigurationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
