export interface IPartenaire {
  id: number;
  description?: string | null;
  nom?: string | null;
  contact?: string | null;
  adresse?: string | null;
  typePartenaire?: string | null;
}

export type NewPartenaire = Omit<IPartenaire, 'id'> & { id: null };
