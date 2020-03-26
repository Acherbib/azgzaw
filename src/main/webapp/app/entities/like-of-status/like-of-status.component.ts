import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILikeOfStatus } from 'app/shared/model/like-of-status.model';
import { LikeOfStatusService } from './like-of-status.service';
import { LikeOfStatusDeleteDialogComponent } from './like-of-status-delete-dialog.component';

@Component({
  selector: 'jhi-like-of-status',
  templateUrl: './like-of-status.component.html'
})
export class LikeOfStatusComponent implements OnInit, OnDestroy {
  likeOfStatuses?: ILikeOfStatus[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected likeOfStatusService: LikeOfStatusService,
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
      this.likeOfStatusService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILikeOfStatus[]>) => (this.likeOfStatuses = res.body ? res.body : []));
      return;
    }
    this.likeOfStatusService.query().subscribe((res: HttpResponse<ILikeOfStatus[]>) => {
      this.likeOfStatuses = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLikeOfStatuses();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILikeOfStatus): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLikeOfStatuses(): void {
    this.eventSubscriber = this.eventManager.subscribe('likeOfStatusListModification', () => this.loadAll());
  }

  delete(likeOfStatus: ILikeOfStatus): void {
    const modalRef = this.modalService.open(LikeOfStatusDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.likeOfStatus = likeOfStatus;
  }
}
