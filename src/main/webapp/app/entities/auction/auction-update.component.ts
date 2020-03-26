import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { IAuction, Auction } from 'app/shared/model/auction.model';
import { AuctionService } from './auction.service';
import { IGift } from 'app/shared/model/gift.model';
import { GiftService } from 'app/entities/gift/gift.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = IGift | IUser;

@Component({
  selector: 'jhi-auction-update',
  templateUrl: './auction-update.component.html'
})
export class AuctionUpdateComponent implements OnInit {
  isSaving = false;

  gifts: IGift[] = [];

  users: IUser[] = [];
  endDateDp: any;

  editForm = this.fb.group({
    id: [],
    cardType: [],
    cardNumber: [],
    endDate: [],
    cvc: [],
    priceOffer: [null, [Validators.min(0)]],
    gift: [],
    author: []
  });

  constructor(
    protected auctionService: AuctionService,
    protected giftService: GiftService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ auction }) => {
      this.updateForm(auction);

      this.giftService
        .query({ filter: 'auction-is-null' })
        .pipe(
          map((res: HttpResponse<IGift[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IGift[]) => {
          if (!auction.gift || !auction.gift.id) {
            this.gifts = resBody;
          } else {
            this.giftService
              .find(auction.gift.id)
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

  updateForm(auction: IAuction): void {
    this.editForm.patchValue({
      id: auction.id,
      cardType: auction.cardType,
      cardNumber: auction.cardNumber,
      endDate: auction.endDate,
      cvc: auction.cvc,
      priceOffer: auction.priceOffer,
      gift: auction.gift,
      author: auction.author
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const auction = this.createFromForm();
    if (auction.id !== undefined) {
      this.subscribeToSaveResponse(this.auctionService.update(auction));
    } else {
      this.subscribeToSaveResponse(this.auctionService.create(auction));
    }
  }

  private createFromForm(): IAuction {
    return {
      ...new Auction(),
      id: this.editForm.get(['id'])!.value,
      cardType: this.editForm.get(['cardType'])!.value,
      cardNumber: this.editForm.get(['cardNumber'])!.value,
      endDate: this.editForm.get(['endDate'])!.value,
      cvc: this.editForm.get(['cvc'])!.value,
      priceOffer: this.editForm.get(['priceOffer'])!.value,
      gift: this.editForm.get(['gift'])!.value,
      author: this.editForm.get(['author'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuction>>): void {
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
