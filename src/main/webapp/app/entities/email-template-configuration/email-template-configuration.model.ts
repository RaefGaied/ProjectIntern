import dayjs from 'dayjs/esm';
import { Act } from 'app/entities/enumerations/act.model';

export interface IEmailTemplateConfiguration {
  id: number;
  nomTemplate?: string | null;
  corps?: string | null;
  datedeCreation?: dayjs.Dayjs | null;
  datedeModification?: dayjs.Dayjs | null;
  activeStatus?: keyof typeof Act | null;
}

export type NewEmailTemplateConfiguration = Omit<IEmailTemplateConfiguration, 'id'> & { id: null };
