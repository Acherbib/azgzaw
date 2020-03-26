import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILikeOfGift } from 'app/shared/model/like-of-gift.model';
import { LikeOfGiftService } from './like-of-gift.service';
import { LikeOfGiftDeleteDialogComponent } from './like-of-gift-delete-dialog.component';

@Component({
  selector: 'jhi-like-of-gift',
  templateUrl: './like-of-gift.component.html'
})
export class LikeOfGiftComponent implements OnInit, OnDestroy {
  likeOfGifts?: ILikeOfGift[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected likeOfGiftService: LikeOfGiftService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected activatedRoute: ActivatedRoute
  ) {
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    if (this.currentSearch) {
      this.likeOfGiftService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILikeOfGift[]>) => (this.likeOfGifts = res.body ? res.body : []));
      return;
    }
    this.likeOfGiftService.query().subscribe((res: HttpResponse<ILikeOfGift[]>) => {
      this.likeOfGifts = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLikeOfGifts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILikeOfGift): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLikeOfGifts(): void {
    this.eventSubscriber = this.eventManager.subscribe('likeOfGiftListModification', () => this.loadAll());
  }

  delete(likeOfGift: ILikeOfGift): void {
    const modalRef = this.modalService.open(LikeOfGiftDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.likeOfGift = likeOfGift;
  }
}
