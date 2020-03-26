import { Moment } from 'moment';
import { ICitation } from 'app/shared/model/citation.model';

export interface ISpam {
  id?: string;
  cause?: string;
  dateOfCreation?: Moment;
  citation?: ICitation;
}

export class Spam implements ISpam {
  constructor(public id?: string, public cause?: string, public dateOfCreation?: Moment, public citation?: ICitation) {}
}
