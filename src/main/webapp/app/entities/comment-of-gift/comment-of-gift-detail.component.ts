import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ICommentOfGift } from 'app/shared/model/comment-of-gift.model';

@Component({
  selector: 'jhi-comment-of-gift-detail',
  templateUrl: './comment-of-gift-detail.component.html'
})
export class CommentOfGiftDetailComponent implements OnInit {
  commentOfGift: ICommentOfGift | null = null;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentOfGift }) => {
      this.commentOfGift = commentOfGift;
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
