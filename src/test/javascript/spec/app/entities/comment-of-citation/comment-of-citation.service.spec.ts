import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { CommentOfCitationService } from 'app/entities/comment-of-citation/comment-of-citation.service';
import { ICommentOfCitation, CommentOfCitation } from 'app/shared/model/comment-of-citation.model';

describe('Service Tests', () => {
  describe('CommentOfCitation Service', () => {
    let injector: TestBed;
    let service: CommentOfCitationService;
    let httpMock: HttpTestingController;
    let elemDefault: ICommentOfCitation;
    let expectedResult: ICommentOfCitation | ICommentOfCitation[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CommentOfCitationService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CommentOfCitation('ID', 'AAAAAAA', 'image/png', 'AAAAAAA', currentDate);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfCreation: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        service
          .find('123')
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CommentOfCitation', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateOfCreation: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfCreation: currentDate
          },
          returnedFromService
        );
        service
          .create(new CommentOfCitation())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CommentOfCitation', () => {
        const returnedFromService = Object.assign(
          {
            bodyComment: 'BBBBBB',
            commentOfCitationImage: 'BBBBBB',
            dateOfCreation: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfCreation: currentDate
          },
          returnedFromService
        );
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CommentOfCitation', () => {
        const returnedFromService = Object.assign(
          {
            bodyComment: 'BBBBBB',
            commentOfCitationImage: 'BBBBBB',
            dateOfCreation: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfCreation: currentDate
          },
          returnedFromService
        );
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a CommentOfCitation', () => {
        service.delete('123').subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
