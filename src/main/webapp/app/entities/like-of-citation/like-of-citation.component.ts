import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ILikeOfCitation } from 'app/shared/model/like-of-citation.model';
import { LikeOfCitationService } from './like-of-citation.service';
import { LikeOfCitationDeleteDialogComponent } from './like-of-citation-delete-dialog.component';

@Component({
  selector: 'jhi-like-of-citation',
  templateUrl: './like-of-citation.component.html'
})
export class LikeOfCitationComponent implements OnInit, OnDestroy {
  likeOfCitations?: ILikeOfCitation[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected likeOfCitationService: LikeOfCitationService,
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
      this.likeOfCitationService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ILikeOfCitation[]>) => (this.likeOfCitations = res.body ? res.body : []));
      return;
    }
    this.likeOfCitationService.query().subscribe((res: HttpResponse<ILikeOfCitation[]>) => {
      this.likeOfCitations = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInLikeOfCitations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ILikeOfCitation): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInLikeOfCitations(): void {
    this.eventSubscriber = this.eventManager.subscribe('likeOfCitationListModification', () => this.loadAll());
  }

  delete(likeOfCitation: ILikeOfCitation): void {
    const modalRef = this.modalService.open(LikeOfCitationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.likeOfCitation = likeOfCitation;
  }
}
