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

import { ICommentOfGift, CommentOfGift } from 'app/shared/model/comment-of-gift.model';
import { CommentOfGiftService } from './comment-of-gift.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IGift } from 'app/shared/model/gift.model';
import { GiftService } from 'app/entities/gift/gift.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IGift | IUser;

@Component({
  selector: 'jhi-comment-of-gift-update',
  templateUrl: './comment-of-gift-update.component.html'
})
export class CommentOfGiftUpdateComponent implements OnInit {
  isSaving = false;

  gifts: IGift[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    bodyComment: [null, [Validators.required]],
    commentOfGiftImage: [],
    commentOfGiftImageContentType: [],
    dateOfCreation: [],
    gift: [],
    author: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected commentOfGiftService: CommentOfGiftService,
    protected giftService: GiftService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentOfGift }) => {
      this.updateForm(commentOfGift);

      this.giftService
        .query({ filter: 'commentofgift-is-null' })
        .pipe(
          map((res: HttpResponse<IGift[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IGift[]) => {
          if (!commentOfGift.gift || !commentOfGift.gift.id) {
            this.gifts = resBody;
          } else {
            this.giftService
              .find(commentOfGift.gift.id)
              .pipe(
                map((subRes: HttpResponse<IGift>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IGift[]) => {
                this.gifts = concatRes;
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

  updateForm(commentOfGift: ICommentOfGift): void {
    this.editForm.patchValue({
      id: commentOfGift.id,
      bodyComment: commentOfGift.bodyComment,
      commentOfGiftImage: commentOfGift.commentOfGiftImage,
      commentOfGiftImageContentType: commentOfGift.commentOfGiftImageContentType,
      dateOfCreation: commentOfGift.dateOfCreation != null ? commentOfGift.dateOfCreation.format(DATE_TIME_FORMAT) : null,
      gift: commentOfGift.gift,
      author: commentOfGift.author
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
    const commentOfGift = this.createFromForm();
    if (commentOfGift.id !== undefined) {
      this.subscribeToSaveResponse(this.commentOfGiftService.update(commentOfGift));
    } else {
      this.subscribeToSaveResponse(this.commentOfGiftService.create(commentOfGift));
    }
  }

  private createFromForm(): ICommentOfGift {
    return {
      ...new CommentOfGift(),
      id: this.editForm.get(['id'])!.value,
      bodyComment: this.editForm.get(['bodyComment'])!.value,
      commentOfGiftImageContentType: this.editForm.get(['commentOfGiftImageContentType'])!.value,
      commentOfGiftImage: this.editForm.get(['commentOfGiftImage'])!.value,
      dateOfCreation:
        this.editForm.get(['dateOfCreation'])!.value != null
          ? moment(this.editForm.get(['dateOfCreation'])!.value, DATE_TIME_FORMAT)
          : undefined,
      gift: this.editForm.get(['gift'])!.value,
      author: this.editForm.get(['author'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentOfGift>>): void {
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
