import { Moment } from 'moment';
import { ILocation } from 'app/shared/model/location.model';
import { IUser } from 'app/core/user/user.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';
import { Privacy } from 'app/shared/model/enumerations/privacy.model';

export interface IProfile {
  id?: string;
  numberOfMinutesConnected?: number;
  verified?: boolean;
  joinedDate?: Moment;
  dateOfBirth?: Moment;
  dateOfBirthVisible?: boolean;
  phoneNumber?: number;
  phoneNumberVisible?: boolean;
  aboutMe?: string;
  aboutMeVisible?: boolean;
  gender?: Gender;
  genderVisible?: boolean;
  maritalStatus?: MaritalStatus;
  maritalStatusVisible?: boolean;
  profilePrivacy?: Privacy;
  location?: ILocation;
  user?: IUser;
}

export class Profile implements IProfile {
  constructor(
    public id?: string,
    public numberOfMinutesConnected?: number,
    public verified?: boolean,
    public joinedDate?: Moment,
    public dateOfBirth?: Moment,
    public dateOfBirthVisible?: boolean,
    public phoneNumber?: number,
    public phoneNumberVisible?: boolean,
    public aboutMe?: string,
    public aboutMeVisible?: boolean,
    public gender?: Gender,
    public genderVisible?: boolean,
    public maritalStatus?: MaritalStatus,
    public maritalStatusVisible?: boolean,
    public profilePrivacy?: Privacy,
    public location?: ILocation,
    public user?: IUser
  ) {
    this.verified = this.verified || false;
    this.dateOfBirthVisible = this.dateOfBirthVisible || false;
    this.phoneNumberVisible = this.phoneNumberVisible || false;
    this.aboutMeVisible = this.aboutMeVisible || false;
    this.genderVisible = this.genderVisible || false;
    this.maritalStatusVisible = this.maritalStatusVisible || false;
  }
}
