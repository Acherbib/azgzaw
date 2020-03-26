import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ISpam } from 'app/shared/model/spam.model';
import { SpamService } from './spam.service';
import { SpamDeleteDialogComponent } from './spam-delete-dialog.component';

@Component({
  selector: 'jhi-spam',
  templateUrl: './spam.component.html'
})
export class SpamComponent implements OnInit, OnDestroy {
  spams?: ISpam[];
  eventSubscriber?: Subscription;
  currentSearch: string;

  constructor(
    protected spamService: SpamService,
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
      this.spamService
        .search({
          query: this.currentSearch
        })
        .subscribe((res: HttpResponse<ISpam[]>) => (this.spams = res.body ? res.body : []));
      return;
    }
    this.spamService.query().subscribe((res: HttpResponse<ISpam[]>) => {
      this.spams = res.body ? res.body : [];
      this.currentSearch = '';
    });
  }

  search(query: string): void {
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInSpams();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ISpam): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInSpams(): void {
    this.eventSubscriber = this.eventManager.subscribe('spamListModification', () => this.loadAll());
  }

  delete(spam: ISpam): void {
    const modalRef = this.modalService.open(SpamDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.spam = spam;
  }
}
