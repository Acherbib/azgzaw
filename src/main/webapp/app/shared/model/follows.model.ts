import { Moment } from 'moment';
import { IProfile } from 'app/shared/model/profile.model';
import { IUser } from 'app/core/user/user.model';

export interface IFollows {
  id?: string;
  followingStartDate?: Moment;
  accepted?: boolean;
  blocked?: boolean;
  profile?: IProfile;
  friend?: IUser;
}

export class Follows implements IFollows {
  constructor(
    public id?: string,
    public followingStartDate?: Moment,
    public accepted?: boolean,
    public blocked?: boolean,
    public profile?: IProfile,
    public friend?: IUser
  ) {
    this.accepted = this.accepted || false;
    this.blocked = this.blocked || false;
  }
}
