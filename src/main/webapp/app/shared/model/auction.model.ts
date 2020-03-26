import { Moment } from 'moment';
import { IGift } from 'app/shared/model/gift.model';
import { IUser } from 'app/core/user/user.model';
import { CardType } from 'app/shared/model/enumerations/card-type.model';

export interface IAuction {
  id?: string;
  cardType?: CardType;
  cardNumber?: number;
  endDate?: Moment;
  cvc?: number;
  priceOffer?: number;
  gift?: IGift;
  author?: IUser;
}

export class Auction implements IAuction {
  constructor(
    public id?: string,
    public cardType?: CardType,
    public cardNumber?: number,
    public endDate?: Moment,
    public cvc?: number,
    public priceOffer?: number,
    public gift?: IGift,
    public author?: IUser
  ) {}
}
