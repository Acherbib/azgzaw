import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ICommentOfCitation } from 'app/shared/model/comment-of-citation.model';

type EntityResponseType = HttpResponse<ICommentOfCitation>;
type EntityArrayResponseType = HttpResponse<ICommentOfCitation[]>;

@Injectable({ providedIn: 'root' })
export class CommentOfCitationService {
  public resourceUrl = SERVER_API_URL + 'api/comment-of-citations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/comment-of-citations';

  constructor(protected http: HttpClient) {}

  create(commentOfCitation: ICommentOfCitation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentOfCitation);
    return this.http
      .post<ICommentOfCitation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(commentOfCitation: ICommentOfCitation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(commentOfCitation);
    return this.http
      .put<ICommentOfCitation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICommentOfCitation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommentOfCitation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICommentOfCitation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(commentOfCitation: ICommentOfCitation): ICommentOfCitation {
    const copy: ICommentOfCitation = Object.assign({}, commentOfCitation, {
      dateOfCreation:
        commentOfCitation.dateOfCreation && commentOfCitation.dateOfCreation.isValid()
          ? commentOfCitation.dateOfCreation.toJSON()
          : undefined
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
      res.body.forEach((commentOfCitation: ICommentOfCitation) => {
        commentOfCitation.dateOfCreation = commentOfCitation.dateOfCreation ? moment(commentOfCitation.dateOfCreation) : undefined;
      });
    }
    return res;
  }
}
