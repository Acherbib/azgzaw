import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommentOfStatus } from 'app/shared/model/comment-of-status.model';
import { CommentOfStatusService } from './comment-of-status.service';
import { CommentOfStatusDeleteDialogComponent } from './comment-of-status-delete-dialog.component';

@Component({
  selector: 'jhi-comment-of-status',
  templateUrl: './comment-of-status.component.html'
})
export class CommentOfStatusComponent implements OnInit, OnDestroy {
  commentOfStatuses?: ICommentOfStatus[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected commentOfStatusService: CommentOfStatusService,
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
      this.commentOfStatusService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICommentOfStatus[]>) => (this.commentOfStatuses = res.body ? res.body : []));
      return;
    }
    this.commentOfStatusService.query().subscribe((res: HttpResponse<ICommentOfStatus[]>) => {
      this.commentOfStatuses = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommentOfStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommentOfStatus): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCommentOfStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('commentOfStatusListModification', () => this.loadAll());
  }

  delete(commentOfStatus: ICommentOfStatus): void {
    const modalRef = this.modalService.open(CommentOfStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commentOfStatus = commentOfStatus;
  }
}
