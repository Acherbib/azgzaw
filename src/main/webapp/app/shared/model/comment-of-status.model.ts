import { Moment } from 'moment';
import { IStatus } from 'app/shared/model/status.model';
import { IUser } from 'app/core/user/user.model';

export interface ICommentOfStatus {
  id?: string;
  bodyComment?: string;
  commentOfStatusImageContentType?: string;
  commentOfStatusImage?: any;
  mediaType?: string;
  dateOfCreation?: Moment;
  status?: IStatus;
  author?: IUser;
}

export class CommentOfStatus implements ICommentOfStatus {
  constructor(
    public id?: string,
    public bodyComment?: string,
    public commentOfStatusImageContentType?: string,
    public commentOfStatusImage?: any,
    public mediaType?: string,
    public dateOfCreation?: Moment,
    public status?: IStatus,
    public author?: IUser
  ) {}
}
