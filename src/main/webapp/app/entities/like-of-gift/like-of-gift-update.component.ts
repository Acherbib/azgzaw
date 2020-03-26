import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILikeOfGift, LikeOfGift } from 'app/shared/model/like-of-gift.model';
import { LikeOfGiftService } from './like-of-gift.service';
import { IGift } from 'app/shared/model/gift.model';
import { GiftService } from 'app/entities/gift/gift.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IGift | IUser;

@Component({
  selector: 'jhi-like-of-gift-update',
  templateUrl: './like-of-gift-update.component.html'
})
export class LikeOfGiftUpdateComponent implements OnInit {
  isSaving = false;

  gifts: IGift[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    typeOfLike: [null, [Validators.required]],
    gift: [],
    authorOfLike: []
  });

  constructor(
    protected likeOfGiftService: LikeOfGiftService,
    protected giftService: GiftService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likeOfGift }) => {
      this.updateForm(likeOfGift);

      this.giftService
        .query({ filter: 'likeofgift-is-null' })
        .pipe(
          map((res: HttpResponse<IGift[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IGift[]) => {
          if (!likeOfGift.gift || !likeOfGift.gift.id) {
            this.gifts = resBody;
          } else {
            this.giftService
              .find(likeOfGift.gift.id)
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

  updateForm(likeOfGift: ILikeOfGift): void {
    this.editForm.patchValue({
      id: likeOfGift.id,
      typeOfLike: likeOfGift.typeOfLike,
      gift: likeOfGift.gift,
      authorOfLike: likeOfGift.authorOfLike
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const likeOfGift = this.createFromForm();
    if (likeOfGift.id !== undefined) {
      this.subscribeToSaveResponse(this.likeOfGiftService.update(likeOfGift));
    } else {
      this.subscribeToSaveResponse(this.likeOfGiftService.create(likeOfGift));
    }
  }

  private createFromForm(): ILikeOfGift {
    return {
      ...new LikeOfGift(),
      id: this.editForm.get(['id'])!.value,
      typeOfLike: this.editForm.get(['typeOfLike'])!.value,
      gift: this.editForm.get(['gift'])!.value,
      authorOfLike: this.editForm.get(['authorOfLike'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILikeOfGift>>): void {
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
