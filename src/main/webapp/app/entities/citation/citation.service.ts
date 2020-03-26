import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption, SearchWithPagination } from 'app/shared/util/request-util';
import { ICitation } from 'app/shared/model/citation.model';

type EntityResponseType = HttpResponse<ICitation>;
type EntityArrayResponseType = HttpResponse<ICitation[]>;

@Injectable({ providedIn: 'root' })
export class CitationService {
  public resourceUrl = SERVER_API_URL + 'api/citations';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/citations';

  constructor(protected http: HttpClient) {}

  create(citation: ICitation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(citation);
    return this.http
      .post<ICitation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(citation: ICitation): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(citation);
    return this.http
      .put<ICitation>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http
      .get<ICitation>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICitation[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: string): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req: SearchWithPagination): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICitation[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  protected convertDateFromClient(citation: ICitation): ICitation {
    const copy: ICitation = Object.assign({}, citation, {
      dateOfCreation: citation.dateOfCreation && citation.dateOfCreation.isValid() ? citation.dateOfCreation.toJSON() : undefined
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
      res.body.forEach((citation: ICitation) => {
        citation.dateOfCreation = citation.dateOfCreation ? moment(citation.dateOfCreation) : undefined;
      });
    }
    return res;
  }
}
