import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICommentOfGift } from 'app/shared/model/comment-of-gift.model';

type EntityResponseType = HttpResponse<ICommentOfGift>;
type EntityArrayResponseType = HttpResponse<ICommentOfGift[]>;

@Injectable({ providedIn: 'root' })
export class CommentOfGiftService {
  public resourceUrl = SERVER_API_URL + 'api/comment-of-gifts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/comment-of-gifts';

  constructor(protected http: HttpClient) {}

  create(commentOfGift: ICommentOfGift): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentOfGift);
    return this.http
      .post<ICommentOfGift>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commentOfGift: ICommentOfGift): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentOfGift);
    return this.http
      .put<ICommentOfGift>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICommentOfGift>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommentOfGift[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommentOfGift[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(commentOfGift: ICommentOfGift): ICommentOfGift {
    const copy: ICommentOfGift = Object.assign({}, commentOfGift, {
      dateOfCreation:
        commentOfGift.dateOfCreation && commentOfGift.dateOfCreation.isValid() ? commentOfGift.dateOfCreation.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.dateOfCreation = res.body.dateOfCreation ? moment(res.body.dateOfCreation) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((commentOfGift: ICommentOfGift) => {
        commentOfGift.dateOfCreation = commentOfGift.dateOfCreation ? moment(commentOfGift.dateOfCreation) : undefined;
      });
    }
    return res;
  }
}
