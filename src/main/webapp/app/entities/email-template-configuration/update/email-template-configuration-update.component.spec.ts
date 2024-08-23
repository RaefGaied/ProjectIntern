import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideHttpClient, HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject, from } from 'rxjs';

import { EmailTemplateConfigurationService } from '../service/email-template-configuration.service';
import { IEmailTemplateConfiguration } from '../email-template-configuration.model';
import { EmailTemplateConfigurationFormService } from './email-template-configuration-form.service';

import { EmailTemplateConfigurationUpdateComponent } from './email-template-configuration-update.component';

describe('EmailTemplateConfiguration Management Update Component', () => {
  let comp: EmailTemplateConfigurationUpdateComponent;
  let fixture: ComponentFixture<EmailTemplateConfigurationUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let emailTemplateConfigurationFormService: EmailTemplateConfigurationFormService;
  let emailTemplateConfigurationService: EmailTemplateConfigurationService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [EmailTemplateConfigurationUpdateComponent],
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
      .overrideTemplate(EmailTemplateConfigurationUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EmailTemplateConfigurationUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    emailTemplateConfigurationFormService = TestBed.inject(EmailTemplateConfigurationFormService);
    emailTemplateConfigurationService = TestBed.inject(EmailTemplateConfigurationService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const emailTemplateConfiguration: IEmailTemplateConfiguration = { id: 456 };

      activatedRoute.data = of({ emailTemplateConfiguration });
      comp.ngOnInit();

      expect(comp.emailTemplateConfiguration).toEqual(emailTemplateConfiguration);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailTemplateConfiguration>>();
      const emailTemplateConfiguration = { id: 123 };
      jest.spyOn(emailTemplateConfigurationFormService, 'getEmailTemplateConfiguration').mockReturnValue(emailTemplateConfiguration);
      jest.spyOn(emailTemplateConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailTemplateConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailTemplateConfiguration }));
      saveSubject.complete();

      // THEN
      expect(emailTemplateConfigurationFormService.getEmailTemplateConfiguration).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(emailTemplateConfigurationService.update).toHaveBeenCalledWith(expect.objectContaining(emailTemplateConfiguration));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailTemplateConfiguration>>();
      const emailTemplateConfiguration = { id: 123 };
      jest.spyOn(emailTemplateConfigurationFormService, 'getEmailTemplateConfiguration').mockReturnValue({ id: null });
      jest.spyOn(emailTemplateConfigurationService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailTemplateConfiguration: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: emailTemplateConfiguration }));
      saveSubject.complete();

      // THEN
      expect(emailTemplateConfigurationFormService.getEmailTemplateConfiguration).toHaveBeenCalled();
      expect(emailTemplateConfigurationService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IEmailTemplateConfiguration>>();
      const emailTemplateConfiguration = { id: 123 };
      jest.spyOn(emailTemplateConfigurationService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ emailTemplateConfiguration });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(emailTemplateConfigurationService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
