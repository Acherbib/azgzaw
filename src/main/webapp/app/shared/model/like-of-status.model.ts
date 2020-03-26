import { IStatus } from 'app/shared/model/status.model';
import { IUser } from 'app/core/user/user.model';
import { LikeType } from 'app/shared/model/enumerations/like-type.model';

export interface ILikeOfStatus {
  id?: string;
  typeOfLike?: LikeType;
  status?: IStatus;
  authorOfLike?: IUser;
}

export class LikeOfStatus implements ILikeOfStatus {
  constructor(public id?: string, public typeOfLike?: LikeType, public status?: IStatus, public authorOfLike?: IUser) {}
}
