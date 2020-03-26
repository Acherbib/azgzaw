import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { FollowsService } from 'app/entities/follows/follows.service';
import { IFollows, Follows } from 'app/shared/model/follows.model';

describe('Service Tests', () => {
  describe('Follows Service', () => {
    let injector: TestBed;
    let service: FollowsService;
    let httpMock: HttpTestingController;
    let elemDefault: IFollows;
    let expectedResult: IFollows | IFollows[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(FollowsService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Follows('ID', currentDate, false, false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            followingStartDate: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Follows', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            followingStartDate: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            followingStartDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Follows())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Follows', () => {
        const returnedFromService = Object.assign(
          {
            followingStartDate: currentDate.format(DATE_TIME_FORMAT),
            accepted: true,
            blocked: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            followingStartDate: currentDate
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

      it('should return a list of Follows', () => {
        const returnedFromService = Object.assign(
          {
            followingStartDate: currentDate.format(DATE_TIME_FORMAT),
            accepted: true,
            blocked: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            followingStartDate: currentDate
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

      it('should delete a Follows', () => {
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
