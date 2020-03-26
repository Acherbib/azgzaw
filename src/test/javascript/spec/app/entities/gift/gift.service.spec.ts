import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { GiftService } from 'app/entities/gift/gift.service';
import { IGift, Gift } from 'app/shared/model/gift.model';

describe('Service Tests', () => {
  describe('Gift Service', () => {
    let injector: TestBed;
    let service: GiftService;
    let httpMock: HttpTestingController;
    let elemDefault: IGift;
    let expectedResult: IGift | IGift[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(GiftService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Gift(
        'ID',
        'AAAAAAA',
        'AAAAAAA',
        false,
        false,
        'AAAAAAA',
        'AAAAAAA',
        false,
        0,
        'AAAAAAA',
        0,
        0,
        currentDate,
        'image/png',
        'AAAAAAA'
      );
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

      it('should create a Gift', () => {
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
          .create(new Gift())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Gift', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            isAGift: true,
            reserved: true,
            city: 'BBBBBB',
            country: 'BBBBBB',
            isAuction: true,
            startPrice: 1,
            backgroundColor: 'BBBBBB',
            numberLikesOfGifts: 1,
            numberSeenOfGifts: 1,
            dateOfCreation: currentDate.format(DATE_TIME_FORMAT),
            image: 'BBBBBB'
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

      it('should return a list of Gift', () => {
        const returnedFromService = Object.assign(
          {
            title: 'BBBBBB',
            description: 'BBBBBB',
            isAGift: true,
            reserved: true,
            city: 'BBBBBB',
            country: 'BBBBBB',
            isAuction: true,
            startPrice: 1,
            backgroundColor: 'BBBBBB',
            numberLikesOfGifts: 1,
            numberSeenOfGifts: 1,
            dateOfCreation: currentDate.format(DATE_TIME_FORMAT),
            image: 'BBBBBB'
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

      it('should delete a Gift', () => {
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
