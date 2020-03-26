import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILikeOfStatus, LikeOfStatus } from 'app/shared/model/like-of-status.model';
import { LikeOfStatusService } from './like-of-status.service';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IStatus | IUser;

@Component({
  selector: 'jhi-like-of-status-update',
  templateUrl: './like-of-status-update.component.html'
})
export class LikeOfStatusUpdateComponent implements OnInit {
  isSaving = false;

  statuses: IStatus[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    typeOfLike: [null, [Validators.required]],
    status: [],
    authorOfLike: []
  });

  constructor(
    protected likeOfStatusService: LikeOfStatusService,
    protected statusService: StatusService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likeOfStatus }) => {
      this.updateForm(likeOfStatus);

      this.statusService
        .query({ filter: 'likeofstatus-is-null' })
        .pipe(
          map((res: HttpResponse<IStatus[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IStatus[]) => {
          if (!likeOfStatus.status || !likeOfStatus.status.id) {
            this.statuses = resBody;
          } else {
            this.statusService
              .find(likeOfStatus.status.id)
              .pipe(
                map((subRes: HttpResponse<IStatus>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IStatus[]) => {
                this.statuses = concatRes;
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

  updateForm(likeOfStatus: ILikeOfStatus): void {
    this.editForm.patchValue({
      id: likeOfStatus.id,
      typeOfLike: likeOfStatus.typeOfLike,
      status: likeOfStatus.status,
      authorOfLike: likeOfStatus.authorOfLike
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const likeOfStatus = this.createFromForm();
    if (likeOfStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.likeOfStatusService.update(likeOfStatus));
    } else {
      this.subscribeToSaveResponse(this.likeOfStatusService.create(likeOfStatus));
    }
  }

  private createFromForm(): ILikeOfStatus {
    return {
      ...new LikeOfStatus(),
      id: this.editForm.get(['id'])!.value,
      typeOfLike: this.editForm.get(['typeOfLike'])!.value,
      status: this.editForm.get(['status'])!.value,
      authorOfLike: this.editForm.get(['authorOfLike'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILikeOfStatus>>): void {
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
