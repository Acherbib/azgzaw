import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IGift {
  id?: string;
  title?: string;
  description?: string;
  isAGift?: boolean;
  reserved?: boolean;
  city?: string;
  country?: string;
  isAuction?: boolean;
  startPrice?: number;
  backgroundColor?: string;
  numberLikesOfGifts?: number;
  numberSeenOfGifts?: number;
  dateOfCreation?: Moment;
  imageContentType?: string;
  image?: any;
  authorOfGift?: IUser;
}

export class Gift implements IGift {
  constructor(
    public id?: string,
    public title?: string,
    public description?: string,
    public isAGift?: boolean,
    public reserved?: boolean,
    public city?: string,
    public country?: string,
    public isAuction?: boolean,
    public startPrice?: number,
    public backgroundColor?: string,
    public numberLikesOfGifts?: number,
    public numberSeenOfGifts?: number,
    public dateOfCreation?: Moment,
    public imageContentType?: string,
    public image?: any,
    public authorOfGift?: IUser
  ) {
    this.isAGift = this.isAGift || false;
    this.reserved = this.reserved || false;
    this.isAuction = this.isAuction || false;
  }
}
