import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { MessageService } from 'app/entities/message/message.service';
import { IMessage, Message } from 'app/shared/model/message.model';

describe('Service Tests', () => {
  describe('Message Service', () => {
    let injector: TestBed;
    let service: MessageService;
    let httpMock: HttpTestingController;
    let elemDefault: IMessage;
    let expectedResult: IMessage | IMessage[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(MessageService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Message('ID', 'AAAAAAA', currentDate, 'image/png', 'AAAAAAA', false);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            dateOfSend: currentDate.format(DATE_TIME_FORMAT)
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

      it('should create a Message', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            dateOfSend: currentDate.format(DATE_TIME_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfSend: currentDate
          },
          returnedFromService
        );
        service
          .create(new Message())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Message', () => {
        const returnedFromService = Object.assign(
          {
            content: 'BBBBBB',
            dateOfSend: currentDate.format(DATE_TIME_FORMAT),
            media: 'BBBBBB',
            seen: true
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            dateOfSend: currentDate
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

      it('should return a list of Message', () => {
        const returnedFromService = Object.assign(
          {
            content: 'BBBBBB',
            dateOfSend: currentDate.format(DATE_TIME_FORMAT),
            media: 'BBBBBB',
            seen: true
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            dateOfSend: currentDate
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

      it('should delete a Message', () => {
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
