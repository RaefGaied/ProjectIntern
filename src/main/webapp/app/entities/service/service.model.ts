import { IHotel } from 'app/entities/hotel/hotel.model';
import { IPartenaire } from 'app/entities/partenaire/partenaire.model';
import { IReservation } from 'app/entities/reservation/reservation.model';

export interface IService {
  id: number;
  nom?: string | null;
  description?: string | null;
  prix?: number | null;
  disponibilite?: string | null;
  capacite?: number | null;
  typeService?: string | null;
  hotel?: Pick<IHotel, 'id'> | null;
  partenaire?: Pick<IPartenaire, 'id'> | null;
  reservation?: Pick<IReservation, 'id'> | null;
}

export type NewService = Omit<IService, 'id'> & { id: null };
