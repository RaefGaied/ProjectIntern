import dayjs from 'dayjs/esm';

export interface IUIConfiguration {
  id: number;
  colorSchema?: string | null;
  logo?: string | null;
  banner?: string | null;
  dateCreation?: dayjs.Dayjs | null;
  dateModify?: dayjs.Dayjs | null;
}

export type NewUIConfiguration = Omit<IUIConfiguration, 'id'> & { id: null };
