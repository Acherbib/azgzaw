import { Moment } from 'moment';
import { IGift } from 'app/shared/model/gift.model';
import { IUser } from 'app/core/user/user.model';

export interface ICommentOfGift {
  id?: string;
  bodyComment?: string;
  commentOfGiftImageContentType?: string;
  commentOfGiftImage?: any;
  dateOfCreation?: Moment;
  gift?: IGift;
  author?: IUser;
}

export class CommentOfGift implements ICommentOfGift {
  constructor(
    public id?: string,
    public bodyComment?: string,
    public commentOfGiftImageContentType?: string,
    public commentOfGiftImage?: any,
    public dateOfCreation?: Moment,
    public gift?: IGift,
    public author?: IUser
  ) {}
}
