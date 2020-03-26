import { IUser } from 'app/core/user/user.model';
import { Privacy } from 'app/shared/model/enumerations/privacy.model';

export interface IStatus {
  id?: string;
  description?: string;
  statusMediaContentType?: string;
  statusMedia?: any;
  numberSeenOfStatus?: number;
  statusPrivacy?: Privacy;
  authorOfStatus?: IUser;
}

export class Status implements IStatus {
  constructor(
    public id?: string,
    public description?: string,
    public statusMediaContentType?: string,
    public statusMedia?: any,
    public numberSeenOfStatus?: number,
    public statusPrivacy?: Privacy,
    public authorOfStatus?: IUser
  ) {}
}
