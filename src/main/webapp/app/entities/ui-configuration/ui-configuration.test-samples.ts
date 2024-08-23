import dayjs from 'dayjs/esm';

import { IUIConfiguration, NewUIConfiguration } from './ui-configuration.model';

export const sampleWithRequiredData: IUIConfiguration = {
  id: 2675,
};

export const sampleWithPartialData: IUIConfiguration = {
  id: 21803,
  colorSchema: 'regarding',
  banner: 'often',
  dateCreation: dayjs('2024-08-05T07:48'),
  dateModify: dayjs('2024-08-05T00:42'),
};

export const sampleWithFullData: IUIConfiguration = {
  id: 28044,
  colorSchema: 'yuck suspend',
  logo: 'when woot oof',
  banner: 'overview out happily',
  dateCreation: dayjs('2024-08-05T09:06'),
  dateModify: dayjs('2024-08-04T19:14'),
};

export const sampleWithNewData: NewUIConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
