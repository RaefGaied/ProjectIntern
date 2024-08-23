import { IAuthentificationConfiguration, NewAuthentificationConfiguration } from './authentification-configuration.model';

export const sampleWithRequiredData: IAuthentificationConfiguration = {
  id: 4963,
};

export const sampleWithPartialData: IAuthentificationConfiguration = {
  id: 317,
  loginPageCustomization: 'violently',
};

export const sampleWithFullData: IAuthentificationConfiguration = {
  id: 23793,
  twoFactorEnabled: true,
  loginPageCustomization: 'clasp junior per',
};

export const sampleWithNewData: NewAuthentificationConfiguration = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
