import { IHotelAdministrateur, NewHotelAdministrateur } from './hotel-administrateur.model';

export const sampleWithRequiredData: IHotelAdministrateur = {
  id: 12430,
};

export const sampleWithPartialData: IHotelAdministrateur = {
  id: 7674,
  nom: 'dirty because',
};

export const sampleWithFullData: IHotelAdministrateur = {
  id: 20330,
  nom: 'stuff',
  email: 'Dorthy.Tillman48@gmail.com',
  motDePasse: 'icy welcome',
};

export const sampleWithNewData: NewHotelAdministrateur = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
