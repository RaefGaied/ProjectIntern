import dayjs from 'dayjs/esm';

import { IReservation, NewReservation } from './reservation.model';

export const sampleWithRequiredData: IReservation = {
  id: 2187,
};

export const sampleWithPartialData: IReservation = {
  id: 3544,
  dateDebut: dayjs('2024-08-04T20:52'),
  dateFin: dayjs('2024-08-04T16:36'),
  nombrePersonnes: 18196,
  statutPaiement: 'EN_COURS',
};

export const sampleWithFullData: IReservation = {
  id: 17723,
  dateDebut: dayjs('2024-08-04T19:24'),
  dateFin: dayjs('2024-08-05T02:30'),
  nombrePersonnes: 22120,
  totalPrix: 30793.08,
  statutPaiement: 'CONFIRME',
};

export const sampleWithNewData: NewReservation = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
