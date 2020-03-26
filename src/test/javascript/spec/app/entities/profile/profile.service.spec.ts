import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT, DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { ProfileService } from 'app/entities/profile/profile.service';
import { IProfile, Profile } from 'app/shared/model/profile.model';
import { Gender } from 'app/shared/model/enumerations/gender.model';
import { MaritalStatus } from 'app/shared/model/enumerations/marital-status.model';
import { Privacy } from 'app/shared/model/enumerations/privacy.model';

describe('Service Tests', () => {
  describe('Profile Service', () => {
    let injector: TestBed;
    let service: ProfileService;
    let httpMock: HttpTestingController;
    let elemDefault: IProfile;
    let expectedResult: IProfile | IProfile[] | boolean | null;
    let currentDate: moment.Moment;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProfileService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new Profile(
        'ID',
        0,
        false,
        currentDate,
        currentDate,
        false,
        0,
        false,
        'AAAAAAA',
        false,
        Gender.MALE,
        false,
        MaritalStatus.SINGLE,
        false,
        Privacy.PUBLIC
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            joinedDate: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT)
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

      it('should create a Profile', () => {
        const returnedFromService = Object.assign(
          {
            id: 'ID',
            joinedDate: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT)
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            joinedDate: currentDate,
            dateOfBirth: currentDate
          },
          returnedFromService
        );
        service
          .create(new Profile())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Profile', () => {
        const returnedFromService = Object.assign(
          {
            numberOfMinutesConnected: 1,
            verified: true,
            joinedDate: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT),
            dateOfBirthVisible: true,
            phoneNumber: 1,
            phoneNumberVisible: true,
            aboutMe: 'BBBBBB',
            aboutMeVisible: true,
            gender: 'BBBBBB',
            genderVisible: true,
            maritalStatus: 'BBBBBB',
            maritalStatusVisible: true,
            profilePrivacy: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            joinedDate: currentDate,
            dateOfBirth: currentDate
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

      it('should return a list of Profile', () => {
        const returnedFromService = Object.assign(
          {
            numberOfMinutesConnected: 1,
            verified: true,
            joinedDate: currentDate.format(DATE_TIME_FORMAT),
            dateOfBirth: currentDate.format(DATE_FORMAT),
            dateOfBirthVisible: true,
            phoneNumber: 1,
            phoneNumberVisible: true,
            aboutMe: 'BBBBBB',
            aboutMeVisible: true,
            gender: 'BBBBBB',
            genderVisible: true,
            maritalStatus: 'BBBBBB',
            maritalStatusVisible: true,
            profilePrivacy: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign(
          {
            joinedDate: currentDate,
            dateOfBirth: currentDate
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

      it('should delete a Profile', () => {
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
