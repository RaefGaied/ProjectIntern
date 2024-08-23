import { IUIConfiguration } from 'app/entities/ui-configuration/ui-configuration.model';
import { IEmailTemplateConfiguration } from 'app/entities/email-template-configuration/email-template-configuration.model';
import { IAuthentificationConfiguration } from 'app/entities/authentification-configuration/authentification-configuration.model';
import { IHotelAdministrateur } from 'app/entities/hotel-administrateur/hotel-administrateur.model';

export interface IHotel {
  id: number;
  nom?: string | null;
  adresse?: string | null;
  numeroTelephone?: number | null;
  pays?: string | null;
  ville?: string | null;
  vueS?: string | null;
  capacite?: number | null;
  notation?: string | null;
  lienUnique?: string | null;
  uiConfigurations?: Pick<IUIConfiguration, 'id'> | null;
  emailConfig?: Pick<IEmailTemplateConfiguration, 'id'> | null;
  authConfig?: Pick<IAuthentificationConfiguration, 'id'> | null;
  hotelAdministrateur?: Pick<IHotelAdministrateur, 'id'> | null;
}

export type NewHotel = Omit<IHotel, 'id'> & { id: null };
