import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IProfile, Profile } from 'app/shared/model/profile.model';
import { ProfileService } from './profile.service';
import { ILocation } from 'app/shared/model/location.model';
import { LocationService } from 'app/entities/location/location.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ILocation | IUser;

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html'
})
export class ProfileUpdateComponent implements OnInit {
  isSaving = false;

  locations: ILocation[] = [];

  users: IUser[] = [];
  dateOfBirthDp: any;

  editForm = this.fb.group({
    id: [],
    numberOfMinutesConnected: [],
    verified: [],
    joinedDate: [],
    dateOfBirth: [],
    dateOfBirthVisible: [],
    phoneNumber: [],
    phoneNumberVisible: [],
    aboutMe: [],
    aboutMeVisible: [],
    gender: [],
    genderVisible: [],
    maritalStatus: [],
    maritalStatusVisible: [],
    profilePrivacy: [null, [Validators.required]],
    location: [],
    user: []
  });

  constructor(
    protected profileService: ProfileService,
    protected locationService: LocationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.locationService
        .query({ filter: 'profile-is-null' })
        .pipe(
          map((res: HttpResponse<ILocation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ILocation[]) => {
          if (!profile.location || !profile.location.id) {
            this.locations = resBody;
          } else {
            this.locationService
              .find(profile.location.id)
              .pipe(
                map((subRes: HttpResponse<ILocation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ILocation[]) => {
                this.locations = concatRes;
              });
          }
        });

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      id: profile.id,
      numberOfMinutesConnected: profile.numberOfMinutesConnected,
      verified: profile.verified,
      joinedDate: profile.joinedDate != null ? profile.joinedDate.format(DATE_TIME_FORMAT) : null,
      dateOfBirth: profile.dateOfBirth,
      dateOfBirthVisible: profile.dateOfBirthVisible,
      phoneNumber: profile.phoneNumber,
      phoneNumberVisible: profile.phoneNumberVisible,
      aboutMe: profile.aboutMe,
      aboutMeVisible: profile.aboutMeVisible,
      gender: profile.gender,
      genderVisible: profile.genderVisible,
      maritalStatus: profile.maritalStatus,
      maritalStatusVisible: profile.maritalStatusVisible,
      profilePrivacy: profile.profilePrivacy,
      location: profile.location,
      user: profile.user
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  private createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
      numberOfMinutesConnected: this.editForm.get(['numberOfMinutesConnected'])!.value,
      verified: this.editForm.get(['verified'])!.value,
      joinedDate:
        this.editForm.get(['joinedDate'])!.value != null ? moment(this.editForm.get(['joinedDate'])!.value, DATE_TIME_FORMAT) : undefined,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      dateOfBirthVisible: this.editForm.get(['dateOfBirthVisible'])!.value,
      phoneNumber: this.editForm.get(['phoneNumber'])!.value,
      phoneNumberVisible: this.editForm.get(['phoneNumberVisible'])!.value,
      aboutMe: this.editForm.get(['aboutMe'])!.value,
      aboutMeVisible: this.editForm.get(['aboutMeVisible'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      genderVisible: this.editForm.get(['genderVisible'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      maritalStatusVisible: this.editForm.get(['maritalStatusVisible'])!.value,
      profilePrivacy: this.editForm.get(['profilePrivacy'])!.value,
      location: this.editForm.get(['location'])!.value,
      user: this.editForm.get(['user'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
