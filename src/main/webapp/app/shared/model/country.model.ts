export interface ICountry {
  id?: string;
  countryName?: string;
}

export class Country implements ICountry {
  constructor(public id?: string, public countryName?: string) {}
}
