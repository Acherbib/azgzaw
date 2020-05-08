import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiDataUtils } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICitation } from 'app/shared/model/citation.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CitationService } from './citation.service';
import { CitationDeleteDialogComponent } from './citation-delete-dialog.component';
import { AccountService } from 'app/core/auth/account.service';
import { NgxSpinnerService } from 'ngx-spinner';

@Component({
  selector: 'jhi-citation',
  templateUrl: './citation.component.html'
})
export class CitationComponent implements OnInit, OnDestroy {
  citations: ICitation[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;
  currentSearch: string;
  login: string | null = null;

  constructor(
    private spinner: NgxSpinnerService,
    private accountService: AccountService,
    protected citationService: CitationService,
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks,
    protected activatedRoute: ActivatedRoute
  ) {
    this.citations = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0
    };
    this.predicate = 'id';
    this.ascending = true;
    this.currentSearch =
      this.activatedRoute.snapshot && this.activatedRoute.snapshot.queryParams['search']
        ? this.activatedRoute.snapshot.queryParams['search']
        : '';
  }

  loadAll(): void {
    this.spinner.show();
    if (this.currentSearch) {
      this.citationService
        .search({
          query: this.currentSearch,
          page: this.page,
          size: this.itemsPerPage,
          sort: this.sort()
        })
        .subscribe((res: HttpResponse<ICitation[]>) => {
          this.paginateCitations(res.body, res.headers);
          this.spinner.hide();
        });
      return;
    }
    this.citationService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort()
      })
      .subscribe((res: HttpResponse<ICitation[]>) => {
        this.paginateCitations(res.body, res.headers);
        this.spinner.hide();
      });
  }

  reset(): void {
    this.page = 0;
    this.citations = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  search(query: string): void {
    this.citations = [];
    this.links = {
      last: 0
    };
    this.page = 0;
    if (query) {
      this.predicate = '_score';
      this.ascending = false;
    } else {
      this.predicate = 'id';
      this.ascending = true;
    }
    this.currentSearch = query;
    this.loadAll();
  }

  ngOnInit(): void {
    this.accountService.identity().subscribe(account => {
      if (account) {
        this.login = account.login;
      }
    });
    this.loadAll();
    this.registerChangeInCitations();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICitation): string {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    return this.dataUtils.openFile(contentType, base64String);
  }

  registerChangeInCitations(): void {
    this.eventSubscriber = this.eventManager.subscribe('citationListModification', () => {
      this.reset();
    });
  }

  delete(citation: ICitation): void {
    const modalRef = this.modalService.open(CitationDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.citation = citation;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCitations(data: ICitation[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      data.reverse();
      for (let i = 0; i < data.length; i++) {
        this.citations.push(data[i]);
      }
    }
  }

  isAuthenticated(): boolean {
    return this.accountService.isAuthenticated();
  }

  getImageUrl(): string {
    return this.isAuthenticated() ? this.accountService.getImageUrl() : '';
  }
}
