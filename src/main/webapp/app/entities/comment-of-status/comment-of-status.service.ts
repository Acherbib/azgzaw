import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICommentOfStatus } from 'app/shared/model/comment-of-status.model';

type EntityResponseType = HttpResponse<ICommentOfStatus>;
type EntityArrayResponseType = HttpResponse<ICommentOfStatus[]>;

@Injectable({ providedIn: 'root' })
export class CommentOfStatusService {
  public resourceUrl = SERVER_API_URL + 'api/comment-of-statuses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/comment-of-statuses';

  constructor(protected http: HttpClient) {}

  create(commentOfStatus: ICommentOfStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentOfStatus);
    return this.http
      .post<ICommentOfStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commentOfStatus: ICommentOfStatus): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentOfStatus);
    return this.http
      .put<ICommentOfStatus>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICommentOfStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommentOfStatus[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommentOfStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(commentOfStatus: ICommentOfStatus): ICommentOfStatus {
    const copy: ICommentOfStatus = Object.assign({}, commentOfStatus, {
      dateOfCreation:
        commentOfStatus.dateOfCreation && commentOfStatus.dateOfCreation.isValid() ? commentOfStatus.dateOfCreation.toJSON() : undefined
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
      res.body.forEach((commentOfStatus: ICommentOfStatus) => {
        commentOfStatus.dateOfCreation = commentOfStatus.dateOfCreation ? moment(commentOfStatus.dateOfCreation) : undefined;
      });
    }
    return res;
  }
}
