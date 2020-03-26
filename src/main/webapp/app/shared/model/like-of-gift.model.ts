import { IGift } from 'app/shared/model/gift.model';
import { IUser } from 'app/core/user/user.model';
import { LikeType } from 'app/shared/model/enumerations/like-type.model';

export interface ILikeOfGift {
  id?: string;
  typeOfLike?: LikeType;
  gift?: IGift;
  authorOfLike?: IUser;
}

export class LikeOfGift implements ILikeOfGift {
  constructor(public id?: string, public typeOfLike?: LikeType, public gift?: IGift, public authorOfLike?: IUser) {}
}
