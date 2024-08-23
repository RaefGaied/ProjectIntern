import { IService, NewService } from './service.model';

export const sampleWithRequiredData: IService = {
  id: 19650,
};

export const sampleWithPartialData: IService = {
  id: 4964,
  description: 'among instead lost',
  prix: 17723.54,
  disponibilite: 'aboard although',
  capacite: 27737,
  typeService: 'puny',
};

export const sampleWithFullData: IService = {
  id: 9137,
  nom: 'until',
  description: 'bah persevere',
  prix: 26239.39,
  disponibilite: 'gauge',
  capacite: 14577,
  typeService: 'questionably how',
};

export const sampleWithNewData: NewService = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
