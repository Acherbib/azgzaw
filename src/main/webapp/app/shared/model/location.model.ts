import { ICountry } from 'app/shared/model/country.model';

export interface ILocation {
  id?: string;
  streetAddress?: string;
  postCode?: string;
  city?: string;
  stateProvince?: string;
  country?: ICountry;
}

export class Location implements ILocation {
  constructor(
    public id?: string,
    public streetAddress?: string,
    public postCode?: string,
    public city?: string,
    public stateProvince?: string,
    public country?: ICountry
  ) {}
}
