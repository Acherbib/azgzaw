import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { IFollows } from 'app/shared/model/follows.model';

type EntityResponseType = HttpResponse<IFollows>;
type EntityArrayResponseType = HttpResponse<IFollows[]>;

@Injectable({ providedIn: 'root' })
export class FollowsService {
  public resourceUrl = SERVER_API_URL + 'api/follows';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/follows';

  constructor(protected http: HttpClient) {}

  create(follows: IFollows): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(follows);
    return this.http
      .post<IFollows>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(follows: IFollows): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(follows);
    return this.http
      .put<IFollows>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<IFollows>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFollows[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFollows[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(follows: IFollows): IFollows {
    const copy: IFollows = Object.assign({}, follows, {
      followingStartDate:
        follows.followingStartDate && follows.followingStartDate.isValid() ? follows.followingStartDate.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.followingStartDate = res.body.followingStartDate ? moment(res.body.followingStartDate) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((follows: IFollows) => {
        follows.followingStartDate = follows.followingStartDate ? moment(follows.followingStartDate) : undefined;
      });
    }
    return res;
  }
}
