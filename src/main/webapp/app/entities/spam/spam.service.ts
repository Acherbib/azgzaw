import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ISpam } from 'app/shared/model/spam.model';

type EntityResponseType = HttpResponse<ISpam>;
type EntityArrayResponseType = HttpResponse<ISpam[]>;

@Injectable({ providedIn: 'root' })
export class SpamService {
  public resourceUrl = SERVER_API_URL + 'api/spams';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/spams';

  constructor(protected http: HttpClient) {}

  create(spam: ISpam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(spam);
    return this.http
      .post<ISpam>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(spam: ISpam): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(spam);
    return this.http
      .put<ISpam>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ISpam>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpam[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpam[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(spam: ISpam): ISpam {
    const copy: ISpam = Object.assign({}, spam, {
      dateOfCreation: spam.dateOfCreation && spam.dateOfCreation.isValid() ? spam.dateOfCreation.toJSON() : undefined
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
      res.body.forEach((spam: ISpam) => {
        spam.dateOfCreation = spam.dateOfCreation ? moment(spam.dateOfCreation) : undefined;
      });
    }
    return res;
  }
}
