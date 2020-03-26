import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';
import { Privacy } from 'app/shared/model/enumerations/privacy.model';

export interface ICitation {
  id?: string;
  description?: string;
  greenCitationMediaContentType?: string;
  greenCitationMedia?: any;
  dateOfCreation?: Moment;
  backgroundColor?: string;
  greenCitationPrivacy?: Privacy;
  authorOfCitation?: IUser;
}

export class Citation implements ICitation {
  constructor(
    public id?: string,
    public description?: string,
    public greenCitationMediaContentType?: string,
    public greenCitationMedia?: any,
    public dateOfCreation?: Moment,
    public backgroundColor?: string,
    public greenCitationPrivacy?: Privacy,
    public authorOfCitation?: IUser
  ) {}
}
