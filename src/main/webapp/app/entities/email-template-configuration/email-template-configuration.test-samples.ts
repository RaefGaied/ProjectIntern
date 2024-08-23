import dayjs from 'dayjs/esm';

import { IEmailTemplateConfiguration, NewEmailTemplateConfiguration } from './email-template-configuration.model';

export const sampleWithRequiredData: IEmailTemplateConfiguration = {
  id: 30937,
};

export const sampleWithPartialData: IEmailTemplateConfiguration = {
  id: 25973,
  corps: 'yowza tightly',
  datedeCreation: dayjs('2024-08-05T08:15'),
};

export const sampleWithFullData: IEmailTemplateConfiguration = {
  id: 8176,
  nomTemplate: 'trounce',
  corps: 'moose cheerfully',
  datedeCreation: dayjs('2024-08-04T18:26'),
  datedeModification: dayjs('2024-08-04T16:16'),
  activeStatus: 'AVAILABLE',
};

export const sampleWithNewData: NewEmailTemplateConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
