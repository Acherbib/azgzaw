import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, Search } from 'app/shared/util/request-util';
import { ILikeOfCitation } from 'app/shared/model/like-of-citation.model';

type EntityResponseType = HttpResponse<ILikeOfCitation>;
type EntityArrayResponseType = HttpResponse<ILikeOfCitation[]>;

@Injectable({ providedIn: 'root' })
export class LikeOfCitationService {
  public resourceUrl = SERVER_API_URL + 'api/like-of-citations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/like-of-citations';

  constructor(protected http: HttpClient) {}

  create(likeOfCitation: ILikeOfCitation): Observable<EntityResponseType> {
    return this.http.post<ILikeOfCitation>(this.resourceUrl, likeOfCitation, { observe: 'response' });
  }

  update(likeOfCitation: ILikeOfCitation): Observable<EntityResponseType> {
    return this.http.put<ILikeOfCitation>(this.resourceUrl, likeOfCitation, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<ILikeOfCitation>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILikeOfCitation[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: Search): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ILikeOfCitation[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
