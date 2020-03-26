import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFollows, Follows } from 'app/shared/model/follows.model';
import { FollowsService } from './follows.service';
import { IProfile } from 'app/shared/model/profile.model';
import { ProfileService } from 'app/entities/profile/profile.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IProfile | IUser;

@Component({
  selector: 'jhi-follows-update',
  templateUrl: './follows-update.component.html'
})
export class FollowsUpdateComponent implements OnInit {
  isSaving = false;

  profiles: IProfile[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    followingStartDate: [],
    accepted: [],
    blocked: [],
    profile: [],
    friend: []
  });

  constructor(
    protected followsService: FollowsService,
    protected profileService: ProfileService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ follows }) => {
      this.updateForm(follows);

      this.profileService
        .query({ filter: 'follows-is-null' })
        .pipe(
          map((res: HttpResponse<IProfile[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProfile[]) => {
          if (!follows.profile || !follows.profile.id) {
            this.profiles = resBody;
          } else {
            this.profileService
              .find(follows.profile.id)
              .pipe(
                map((subRes: HttpResponse<IProfile>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IProfile[]) => {
                this.profiles = concatRes;
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

  updateForm(follows: IFollows): void {
    this.editForm.patchValue({
      id: follows.id,
      followingStartDate: follows.followingStartDate != null ? follows.followingStartDate.format(DATE_TIME_FORMAT) : null,
      accepted: follows.accepted,
      blocked: follows.blocked,
      profile: follows.profile,
      friend: follows.friend
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const follows = this.createFromForm();
    if (follows.id !== undefined) {
      this.subscribeToSaveResponse(this.followsService.update(follows));
    } else {
      this.subscribeToSaveResponse(this.followsService.create(follows));
    }
  }

  private createFromForm(): IFollows {
    return {
      ...new Follows(),
      id: this.editForm.get(['id'])!.value,
      followingStartDate:
        this.editForm.get(['followingStartDate'])!.value != null
          ? moment(this.editForm.get(['followingStartDate'])!.value, DATE_TIME_FORMAT)
          : undefined,
      accepted: this.editForm.get(['accepted'])!.value,
      blocked: this.editForm.get(['blocked'])!.value,
      profile: this.editForm.get(['profile'])!.value,
      friend: this.editForm.get(['friend'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFollows>>): void {
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
