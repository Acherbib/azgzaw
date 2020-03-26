import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommentOfGift } from 'app/shared/model/comment-of-gift.model';
import { CommentOfGiftService } from './comment-of-gift.service';
import { CommentOfGiftDeleteDialogComponent } from './comment-of-gift-delete-dialog.component';

@Component({
  selector: 'jhi-comment-of-gift',
  templateUrl: './comment-of-gift.component.html'
})
export class CommentOfGiftComponent implements OnInit, OnDestroy {
  commentOfGifts?: ICommentOfGift[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected commentOfGiftService: CommentOfGiftService,
    protected dataUtils: JhiDataUtils,
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
      this.commentOfGiftService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICommentOfGift[]>) => (this.commentOfGifts = res.body ? res.body : []));
      return;
    }
    this.commentOfGiftService.query().subscribe((res: HttpResponse<ICommentOfGift[]>) => {
      this.commentOfGifts = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommentOfGifts();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommentOfGift): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCommentOfGifts(): void {
    this.eventSubscriber = this.eventManager.subscribe('commentOfGiftListModification', () => this.loadAll());
  }

  delete(commentOfGift: ICommentOfGift): void {
    const modalRef = this.modalService.open(CommentOfGiftDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commentOfGift = commentOfGift;
  }
}
