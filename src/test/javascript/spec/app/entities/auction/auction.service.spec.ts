import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { AuctionService } from 'app/entities/auction/auction.service';
import { IAuction, Auction } from 'app/shared/model/auction.model';
import { CardType } from 'app/shared/model/enumerations/card-type.model';

describe('Service Tests', () => {
  describe('Auction Service', () => {
    let injector: TestBed;
    let service: AuctionService;
    let httpMock: HttpTestingController;
    let elemDefault: IAuction;
    let expectedResult: IAuction | IAuction[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(AuctionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Auction('ID', CardType.VISA, 0, currentDate, 0, 0);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            endDate: currentDate.format(DATE_FORMAT)
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

      it('should create a Auction', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            endDate: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            endDate: currentDate
          },
          returnedFromService
        );
        service
          .create(new Auction())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Auction', () => {
        const returnedFromService = Object.assign(
          {
            cardType: 'BBBBBB',
            cardNumber: 1,
            endDate: currentDate.format(DATE_FORMAT),
            cvc: 1,
            priceOffer: 1
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            endDate: currentDate
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

      it('should return a list of Auction', () => {
        const returnedFromService = Object.assign(
          {
            cardType: 'BBBBBB',
            cardNumber: 1,
            endDate: currentDate.format(DATE_FORMAT),
            cvc: 1,
            priceOffer: 1
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            endDate: currentDate
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

      it('should delete a Auction', () => {
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
