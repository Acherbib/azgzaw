import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IGift } from 'app/shared/model/gift.model';

type EntityResponseType = HttpResponse<IGift>;
type EntityArrayResponseType = HttpResponse<IGift[]>;

@Injectable({ providedIn: 'root' })
export class GiftService {
  public resourceUrl = SERVER_API_URL + 'api/gifts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/gifts';

  constructor(protected http: HttpClient) {}

  create(gift: IGift): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gift);
    return this.http
      .post<IGift>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(gift: IGift): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(gift);
    return this.http
      .put<IGift>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IGift>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGift[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IGift[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(gift: IGift): IGift {
    const copy: IGift = Object.assign({}, gift, {
      dateOfCreation: gift.dateOfCreation && gift.dateOfCreation.isValid() ? gift.dateOfCreation.toJSON() : undefined
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
      res.body.forEach((gift: IGift) => {
        gift.dateOfCreation = gift.dateOfCreation ? moment(gift.dateOfCreation) : undefined;
      });
    }
    return res;
  }
}
