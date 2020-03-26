import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommentOfCitation } from 'app/shared/model/comment-of-citation.model';

@Component({
  selector: 'jhi-comment-of-citation-detail',
  templateUrl: './comment-of-citation-detail.component.html'
})
export class CommentOfCitationDetailComponent implements OnInit {
  commentOfCitation: ICommentOfCitation | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentOfCitation }) => {
      this.commentOfCitation = commentOfCitation;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  previousState(): void {
    window.history.back();
  }
}
