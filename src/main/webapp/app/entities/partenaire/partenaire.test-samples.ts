import { IPartenaire, NewPartenaire } from './partenaire.model';

export const sampleWithRequiredData: IPartenaire = {
  id: 32458,
};

export const sampleWithPartialData: IPartenaire = {
  id: 24263,
  contact: 'linear till',
};

export const sampleWithFullData: IPartenaire = {
  id: 17242,
  description: 'glass searchingly woot',
  nom: 'buffer',
  contact: 'ugh',
  adresse: 'until',
  typePartenaire: 'however',
};

export const sampleWithNewData: NewPartenaire = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
