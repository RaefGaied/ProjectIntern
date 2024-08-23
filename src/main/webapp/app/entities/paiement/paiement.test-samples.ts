import dayjs from 'dayjs/esm';

import { IPaiement, NewPaiement } from './paiement.model';

export const sampleWithRequiredData: IPaiement = {
  id: 6194,
};

export const sampleWithPartialData: IPaiement = {
  id: 17639,
  montant: 10804.84,
  transactionId: 'whenever aw impediment',
  description: 'tasty besides',
  statutPaiement: 'Reussi',
};

export const sampleWithFullData: IPaiement = {
  id: 10791,
  montant: 30392.3,
  datePaiement: dayjs('2024-08-04T20:31'),
  methodePaiement: 'loosely',
  token: 'haunt mmm because',
  transactionId: 'why realistic',
  description: 'often yowza before',
  statutPaiement: 'Echoue',
};

export const sampleWithNewData: NewPaiement = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
