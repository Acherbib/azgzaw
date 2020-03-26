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

import { IGift, Gift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-gift-update',
  templateUrl: './gift-update.component.html'
})
export class GiftUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    description: [],
    isAGift: [],
    reserved: [],
    city: [],
    country: [],
    isAuction: [],
    startPrice: [null, [Validators.min(0)]],
    backgroundColor: [],
    numberLikesOfGifts: [],
    numberSeenOfGifts: [],
    dateOfCreation: [],
    image: [],
    imageContentType: [],
    authorOfGift: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected giftService: GiftService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ gift }) => {
      this.updateForm(gift);

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

  updateForm(gift: IGift): void {
    this.editForm.patchValue({
      id: gift.id,
      title: gift.title,
      description: gift.description,
      isAGift: gift.isAGift,
      reserved: gift.reserved,
      city: gift.city,
      country: gift.country,
      isAuction: gift.isAuction,
      startPrice: gift.startPrice,
      backgroundColor: gift.backgroundColor,
      numberLikesOfGifts: gift.numberLikesOfGifts,
      numberSeenOfGifts: gift.numberSeenOfGifts,
      dateOfCreation: gift.dateOfCreation != null ? gift.dateOfCreation.format(DATE_TIME_FORMAT) : null,
      image: gift.image,
      imageContentType: gift.imageContentType,
      authorOfGift: gift.authorOfGift
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
    const gift = this.createFromForm();
    if (gift.id !== undefined) {
      this.subscribeToSaveResponse(this.giftService.update(gift));
    } else {
      this.subscribeToSaveResponse(this.giftService.create(gift));
    }
  }

  private createFromForm(): IGift {
    return {
      ...new Gift(),
      id: this.editForm.get(['id'])!.value,
      title: this.editForm.get(['title'])!.value,
      description: this.editForm.get(['description'])!.value,
      isAGift: this.editForm.get(['isAGift'])!.value,
      reserved: this.editForm.get(['reserved'])!.value,
      city: this.editForm.get(['city'])!.value,
      country: this.editForm.get(['country'])!.value,
      isAuction: this.editForm.get(['isAuction'])!.value,
      startPrice: this.editForm.get(['startPrice'])!.value,
      backgroundColor: this.editForm.get(['backgroundColor'])!.value,
      numberLikesOfGifts: this.editForm.get(['numberLikesOfGifts'])!.value,
      numberSeenOfGifts: this.editForm.get(['numberSeenOfGifts'])!.value,
      dateOfCreation:
        this.editForm.get(['dateOfCreation'])!.value != null
          ? moment(this.editForm.get(['dateOfCreation'])!.value, DATE_TIME_FORMAT)
          : undefined,
      imageContentType: this.editForm.get(['imageContentType'])!.value,
      image: this.editForm.get(['image'])!.value,
      authorOfGift: this.editForm.get(['authorOfGift'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IGift>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
