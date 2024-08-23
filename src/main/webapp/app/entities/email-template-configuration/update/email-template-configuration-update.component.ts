import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { Act } from 'app/entities/enumerations/act.model';
import { IEmailTemplateConfiguration } from '../email-template-configuration.model';
import { EmailTemplateConfigurationService } from '../service/email-template-configuration.service';
import { EmailTemplateConfigurationFormService, EmailTemplateConfigurationFormGroup } from './email-template-configuration-form.service';

@Component({
  standalone: true,
  selector: 'jhi-email-template-configuration-update',
  templateUrl: './email-template-configuration-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class EmailTemplateConfigurationUpdateComponent implements OnInit {
  isSaving = false;
  emailTemplateConfiguration: IEmailTemplateConfiguration | null = null;
  actValues = Object.keys(Act);

  protected emailTemplateConfigurationService = inject(EmailTemplateConfigurationService);
  protected emailTemplateConfigurationFormService = inject(EmailTemplateConfigurationFormService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: EmailTemplateConfigurationFormGroup = this.emailTemplateConfigurationFormService.createEmailTemplateConfigurationFormGroup();

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ emailTemplateConfiguration }) => {
      this.emailTemplateConfiguration = emailTemplateConfiguration;
      if (emailTemplateConfiguration) {
        this.updateForm(emailTemplateConfiguration);
      }
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const emailTemplateConfiguration = this.emailTemplateConfigurationFormService.getEmailTemplateConfiguration(this.editForm);
    if (emailTemplateConfiguration.id !== null) {
      this.subscribeToSaveResponse(this.emailTemplateConfigurationService.update(emailTemplateConfiguration));
    } else {
      this.subscribeToSaveResponse(this.emailTemplateConfigurationService.create(emailTemplateConfiguration));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmailTemplateConfiguration>>): void {
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

  protected updateForm(emailTemplateConfiguration: IEmailTemplateConfiguration): void {
    this.emailTemplateConfiguration = emailTemplateConfiguration;
    this.emailTemplateConfigurationFormService.resetForm(this.editForm, emailTemplateConfiguration);
  }
}
