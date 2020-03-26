import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommentOfCitation } from 'app/shared/model/comment-of-citation.model';
import { CommentOfCitationService } from './comment-of-citation.service';
import { CommentOfCitationDeleteDialogComponent } from './comment-of-citation-delete-dialog.component';

@Component({
  selector: 'jhi-comment-of-citation',
  templateUrl: './comment-of-citation.component.html'
})
export class CommentOfCitationComponent implements OnInit, OnDestroy {
  commentOfCitations?: ICommentOfCitation[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected commentOfCitationService: CommentOfCitationService,
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
      this.commentOfCitationService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ICommentOfCitation[]>) => (this.commentOfCitations = res.body ? res.body : []));
      return;
    }
    this.commentOfCitationService.query().subscribe((res: HttpResponse<ICommentOfCitation[]>) => {
      this.commentOfCitations = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommentOfCitations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommentOfCitation): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCommentOfCitations(): void {
    this.eventSubscriber = this.eventManager.subscribe('commentOfCitationListModification', () => this.loadAll());
  }

  delete(commentOfCitation: ICommentOfCitation): void {
    const modalRef = this.modalService.open(CommentOfCitationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commentOfCitation = commentOfCitation;
  }
}
