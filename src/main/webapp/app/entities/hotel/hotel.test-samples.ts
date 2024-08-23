import { IHotel, NewHotel } from './hotel.model';

export const sampleWithRequiredData: IHotel = {
  id: 9007,
};

export const sampleWithPartialData: IHotel = {
  id: 2439,
  numeroTelephone: 32016,
  pays: 'hmph',
  ville: 'for disassociate barrel',
  notation: 'supposing instead across',
  lienUnique: 'gah',
};

export const sampleWithFullData: IHotel = {
  id: 5759,
  nom: 'stealthily hungrily thump',
  adresse: 'that while huzzah',
  numeroTelephone: 101,
  pays: 'though pink',
  ville: 'fussy',
  vueS: 'yum unimpressively',
  capacite: 31095,
  notation: 'whenever involve',
  lienUnique: 'incidentally',
};

export const sampleWithNewData: NewHotel = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
