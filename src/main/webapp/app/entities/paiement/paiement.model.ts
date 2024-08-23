import dayjs from 'dayjs/esm';
import { IReservation } from 'app/entities/reservation/reservation.model';
import { PAIEMENT } from 'app/entities/enumerations/paiement.model';

export interface IPaiement {
  id: number;
  montant?: number | null;
  datePaiement?: dayjs.Dayjs | null;
  methodePaiement?: string | null;
  token?: string | null;
  transactionId?: string | null;
  description?: string | null;
  statutPaiement?: keyof typeof PAIEMENT | null;
  reservation?: Pick<IReservation, 'id'> | null;
}

export type NewPaiement = Omit<IPaiement, 'id'> & { id: null };
