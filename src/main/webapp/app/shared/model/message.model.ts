import { Moment } from 'moment';
import { IUser } from 'app/core/user/user.model';

export interface IMessage {
  id?: string;
  content?: string;
  dateOfSend?: Moment;
  mediaContentType?: string;
  media?: any;
  seen?: boolean;
  authorOfMessage?: IUser;
  receiverOfMessage?: IUser;
}

export class Message implements IMessage {
  constructor(
    public id?: string,
    public content?: string,
    public dateOfSend?: Moment,
    public mediaContentType?: string,
    public media?: any,
    public seen?: boolean,
    public authorOfMessage?: IUser,
    public receiverOfMessage?: IUser
  ) {
    this.seen = this.seen || false;
  }
}
