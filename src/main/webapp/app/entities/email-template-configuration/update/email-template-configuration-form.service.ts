import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import dayjs from 'dayjs/esm';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';
import { IEmailTemplateConfiguration, NewEmailTemplateConfiguration } from '../email-template-configuration.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmailTemplateConfiguration for edit and NewEmailTemplateConfigurationFormGroupInput for create.
 */
type EmailTemplateConfigurationFormGroupInput = IEmailTemplateConfiguration | PartialWithRequiredKeyOf<NewEmailTemplateConfiguration>;

/**
 * Type that converts some properties for forms.
 */
type FormValueOf<T extends IEmailTemplateConfiguration | NewEmailTemplateConfiguration> = Omit<
  T,
  'datedeCreation' | 'datedeModification'
> & {
  datedeCreation?: string | null;
  datedeModification?: string | null;
};

type EmailTemplateConfigurationFormRawValue = FormValueOf<IEmailTemplateConfiguration>;

type NewEmailTemplateConfigurationFormRawValue = FormValueOf<NewEmailTemplateConfiguration>;

type EmailTemplateConfigurationFormDefaults = Pick<NewEmailTemplateConfiguration, 'id' | 'datedeCreation' | 'datedeModification'>;

type EmailTemplateConfigurationFormGroupContent = {
  id: FormControl<EmailTemplateConfigurationFormRawValue['id'] | NewEmailTemplateConfiguration['id']>;
  nomTemplate: FormControl<EmailTemplateConfigurationFormRawValue['nomTemplate']>;
  corps: FormControl<EmailTemplateConfigurationFormRawValue['corps']>;
  datedeCreation: FormControl<EmailTemplateConfigurationFormRawValue['datedeCreation']>;
  datedeModification: FormControl<EmailTemplateConfigurationFormRawValue['datedeModification']>;
  activeStatus: FormControl<EmailTemplateConfigurationFormRawValue['activeStatus']>;
};

export type EmailTemplateConfigurationFormGroup = FormGroup<EmailTemplateConfigurationFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmailTemplateConfigurationFormService {
  createEmailTemplateConfigurationFormGroup(
    emailTemplateConfiguration: EmailTemplateConfigurationFormGroupInput = { id: null },
  ): EmailTemplateConfigurationFormGroup {
    const emailTemplateConfigurationRawValue = this.convertEmailTemplateConfigurationToEmailTemplateConfigurationRawValue({
      ...this.getFormDefaults(),
      ...emailTemplateConfiguration,
    });
    return new FormGroup<EmailTemplateConfigurationFormGroupContent>({
      id: new FormControl(
        { value: emailTemplateConfigurationRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomTemplate: new FormControl(emailTemplateConfigurationRawValue.nomTemplate),
      corps: new FormControl(emailTemplateConfigurationRawValue.corps),
      datedeCreation: new FormControl(emailTemplateConfigurationRawValue.datedeCreation),
      datedeModification: new FormControl(emailTemplateConfigurationRawValue.datedeModification),
      activeStatus: new FormControl(emailTemplateConfigurationRawValue.activeStatus),
    });
  }

  getEmailTemplateConfiguration(form: EmailTemplateConfigurationFormGroup): IEmailTemplateConfiguration | NewEmailTemplateConfiguration {
    return this.convertEmailTemplateConfigurationRawValueToEmailTemplateConfiguration(
      form.getRawValue() as EmailTemplateConfigurationFormRawValue | NewEmailTemplateConfigurationFormRawValue,
    );
  }

  resetForm(form: EmailTemplateConfigurationFormGroup, emailTemplateConfiguration: EmailTemplateConfigurationFormGroupInput): void {
    const emailTemplateConfigurationRawValue = this.convertEmailTemplateConfigurationToEmailTemplateConfigurationRawValue({
      ...this.getFormDefaults(),
      ...emailTemplateConfiguration,
    });
    form.reset(
      {
        ...emailTemplateConfigurationRawValue,
        id: { value: emailTemplateConfigurationRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmailTemplateConfigurationFormDefaults {
    const currentTime = dayjs();

    return {
      id: null,
      datedeCreation: currentTime,
      datedeModification: currentTime,
    };
  }

  private convertEmailTemplateConfigurationRawValueToEmailTemplateConfiguration(
    rawEmailTemplateConfiguration: EmailTemplateConfigurationFormRawValue | NewEmailTemplateConfigurationFormRawValue,
  ): IEmailTemplateConfiguration | NewEmailTemplateConfiguration {
    return {
      ...rawEmailTemplateConfiguration,
      datedeCreation: dayjs(rawEmailTemplateConfiguration.datedeCreation, DATE_TIME_FORMAT),
      datedeModification: dayjs(rawEmailTemplateConfiguration.datedeModification, DATE_TIME_FORMAT),
    };
  }

  private convertEmailTemplateConfigurationToEmailTemplateConfigurationRawValue(
    emailTemplateConfiguration:
      | IEmailTemplateConfiguration
      | (Partial<NewEmailTemplateConfiguration> & EmailTemplateConfigurationFormDefaults),
  ): EmailTemplateConfigurationFormRawValue | PartialWithRequiredKeyOf<NewEmailTemplateConfigurationFormRawValue> {
    return {
      ...emailTemplateConfiguration,
      datedeCreation: emailTemplateConfiguration.datedeCreation
        ? emailTemplateConfiguration.datedeCreation.format(DATE_TIME_FORMAT)
        : undefined,
      datedeModification: emailTemplateConfiguration.datedeModification
        ? emailTemplateConfiguration.datedeModification.format(DATE_TIME_FORMAT)
        : undefined,
    };
  }
}
