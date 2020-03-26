import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICommentOfStatus, CommentOfStatus } from 'app/shared/model/comment-of-status.model';
import { CommentOfStatusService } from './comment-of-status.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IStatus } from 'app/shared/model/status.model';
import { StatusService } from 'app/entities/status/status.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IStatus | IUser;

@Component({
  selector: 'jhi-comment-of-status-update',
  templateUrl: './comment-of-status-update.component.html'
})
export class CommentOfStatusUpdateComponent implements OnInit {
  isSaving = false;

  statuses: IStatus[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    bodyComment: [null, [Validators.required]],
    commentOfStatusImage: [],
    commentOfStatusImageContentType: [],
    mediaType: [],
    dateOfCreation: [],
    status: [],
    author: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected commentOfStatusService: CommentOfStatusService,
    protected statusService: StatusService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentOfStatus }) => {
      this.updateForm(commentOfStatus);

      this.statusService
        .query({ filter: 'commentofstatus-is-null' })
        .pipe(
          map((res: HttpResponse<IStatus[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IStatus[]) => {
          if (!commentOfStatus.status || !commentOfStatus.status.id) {
            this.statuses = resBody;
          } else {
            this.statusService
              .find(commentOfStatus.status.id)
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

  updateForm(commentOfStatus: ICommentOfStatus): void {
    this.editForm.patchValue({
      id: commentOfStatus.id,
      bodyComment: commentOfStatus.bodyComment,
      commentOfStatusImage: commentOfStatus.commentOfStatusImage,
      commentOfStatusImageContentType: commentOfStatus.commentOfStatusImageContentType,
      mediaType: commentOfStatus.mediaType,
      dateOfCreation: commentOfStatus.dateOfCreation != null ? commentOfStatus.dateOfCreation.format(DATE_TIME_FORMAT) : null,
      status: commentOfStatus.status,
      author: commentOfStatus.author
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('azgzawApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commentOfStatus = this.createFromForm();
    if (commentOfStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.commentOfStatusService.update(commentOfStatus));
    } else {
      this.subscribeToSaveResponse(this.commentOfStatusService.create(commentOfStatus));
    }
  }

  private createFromForm(): ICommentOfStatus {
    return {
      ...new CommentOfStatus(),
      id: this.editForm.get(['id'])!.value,
      bodyComment: this.editForm.get(['bodyComment'])!.value,
      commentOfStatusImageContentType: this.editForm.get(['commentOfStatusImageContentType'])!.value,
      commentOfStatusImage: this.editForm.get(['commentOfStatusImage'])!.value,
      mediaType: this.editForm.get(['mediaType'])!.value,
      dateOfCreation:
        this.editForm.get(['dateOfCreation'])!.value != null
          ? moment(this.editForm.get(['dateOfCreation'])!.value, DATE_TIME_FORMAT)
          : undefined,
      status: this.editForm.get(['status'])!.value,
      author: this.editForm.get(['author'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentOfStatus>>): void {
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
