import { Moment } from 'moment';
import { ICitation } from 'app/shared/model/citation.model';

export interface ICommentOfCitation {
  id?: string;
  bodyComment?: string;
  commentOfCitationImageContentType?: string;
  commentOfCitationImage?: any;
  dateOfCreation?: Moment;
  citation?: ICitation;
}

export class CommentOfCitation implements ICommentOfCitation {
  constructor(
    public id?: string,
    public bodyComment?: string,
    public commentOfCitationImageContentType?: string,
    public commentOfCitationImage?: any,
    public dateOfCreation?: Moment,
    public citation?: ICitation
  ) {}
}
