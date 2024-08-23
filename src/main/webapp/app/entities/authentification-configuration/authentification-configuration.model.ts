export interface IAuthentificationConfiguration {
  id: number;
  twoFactorEnabled?: boolean | null;
  loginPageCustomization?: string | null;
}

export type NewAuthentificationConfiguration = Omit<IAuthentificationConfiguration, 'id'> & { id: null };
