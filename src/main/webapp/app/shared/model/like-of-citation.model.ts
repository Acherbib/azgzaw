import { ICitation } from 'app/shared/model/citation.model';
import { IUser } from 'app/core/user/user.model';
import { LikeType } from 'app/shared/model/enumerations/like-type.model';

export interface ILikeOfCitation {
  id?: string;
  typeOfLike?: LikeType;
  citation?: ICitation;
  authorOfLike?: IUser;
}

export class LikeOfCitation implements ILikeOfCitation {
  constructor(public id?: string, public typeOfLike?: LikeType, public citation?: ICitation, public authorOfLike?: IUser) {}
}
