import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILikeOfGift } from 'app/shared/model/like-of-gift.model';

type EntityResponseType = HttpResponse<ILikeOfGift>;
type EntityArrayResponseType = HttpResponse<ILikeOfGift[]>;

@Injectable({ providedIn: 'root' })
export class LikeOfGiftService {
  public resourceUrl = SERVER_API_URL + 'api/like-of-gifts';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/like-of-gifts';

  constructor(protected http: HttpClient) {}

  create(likeOfGift: ILikeOfGift): Observable<EntityResponseType> {
    return this.http.post<ILikeOfGift>(this.resourceUrl, likeOfGift, { observe: 'response' });
  }

  update(likeOfGift: ILikeOfGift): Observable<EntityResponseType> {
    return this.http.put<ILikeOfGift>(this.resourceUrl, likeOfGift, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ILikeOfGift>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILikeOfGift[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILikeOfGift[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
