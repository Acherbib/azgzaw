import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILikeOfStatus } from 'app/shared/model/like-of-status.model';

type EntityResponseType = HttpResponse<ILikeOfStatus>;
type EntityArrayResponseType = HttpResponse<ILikeOfStatus[]>;

@Injectable({ providedIn: 'root' })
export class LikeOfStatusService {
  public resourceUrl = SERVER_API_URL + 'api/like-of-statuses';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/like-of-statuses';

  constructor(protected http: HttpClient) {}

  create(likeOfStatus: ILikeOfStatus): Observable<EntityResponseType> {
    return this.http.post<ILikeOfStatus>(this.resourceUrl, likeOfStatus, { observe: 'response' });
  }

  update(likeOfStatus: ILikeOfStatus): Observable<EntityResponseType> {
    return this.http.put<ILikeOfStatus>(this.resourceUrl, likeOfStatus, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ILikeOfStatus>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILikeOfStatus[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILikeOfStatus[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
