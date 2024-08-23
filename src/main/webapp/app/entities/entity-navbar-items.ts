import NavbarItem from 'app/layouts/navbar/navbar-item.model';

export const EntityNavbarItems: NavbarItem[] = [
  {
    name: 'HotelAdministrateur',
    route: '/hotel-administrateur',
    translationKey: 'global.menu.entities.hotelAdministrateur',
  },
  {
    name: 'Hotel',
    route: '/hotel',
    translationKey: 'global.menu.entities.hotel',
  },
  {
    name: 'UIConfiguration',
    route: '/ui-configuration',
    translationKey: 'global.menu.entities.uIConfiguration',
  },
  {
    name: 'EmailTemplateConfiguration',
    route: '/email-template-configuration',
    translationKey: 'global.menu.entities.emailTemplateConfiguration',
  },
  {
    name: 'AuthentificationConfiguration',
    route: '/authentification-configuration',
    translationKey: 'global.menu.entities.authentificationConfiguration',
  },
  {
    name: 'Service',
    route: '/service',
    translationKey: 'global.menu.entities.service',
  },
  {
    name: 'Partenaire',
    route: '/partenaire',
    translationKey: 'global.menu.entities.partenaire',
  },
  {
    name: 'Reservation',
    route: '/reservation',
    translationKey: 'global.menu.entities.reservation',
  },
  {
    name: 'Paiement',
    route: '/paiement',
    translationKey: 'global.menu.entities.paiement',
  },
];
