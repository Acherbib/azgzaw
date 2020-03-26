import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { IAuction } from 'app/shared/model/auction.model';

type EntityResponseType = HttpResponse<IAuction>;
type EntityArrayResponseType = HttpResponse<IAuction[]>;

@Injectable({ providedIn: 'root' })
export class AuctionService {
  public resourceUrl = SERVER_API_URL + 'api/auctions';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/auctions';

  constructor(protected http: HttpClient) {}

  create(auction: IAuction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auction);
    return this.http
      .post<IAuction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(auction: IAuction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(auction);
    return this.http
      .put<IAuction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IAuction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAuction[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(auction: IAuction): IAuction {
    const copy: IAuction = Object.assign({}, auction, {
      endDate: auction.endDate && auction.endDate.isValid() ? auction.endDate.format(DATE_FORMAT) : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.endDate = res.body.endDate ? moment(res.body.endDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((auction: IAuction) => {
        auction.endDate = auction.endDate ? moment(auction.endDate) : undefined;
      });
    }
    return res;
  }
}
