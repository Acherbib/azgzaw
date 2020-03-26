import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommentOfStatus } from 'app/shared/model/comment-of-status.model';

@Component({
  selector: 'jhi-comment-of-status-detail',
  templateUrl: './comment-of-status-detail.component.html'
})
export class CommentOfStatusDetailComponent implements OnInit {
  commentOfStatus: ICommentOfStatus | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentOfStatus }) => {
      this.commentOfStatus = commentOfStatus;
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
