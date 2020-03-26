import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommentOfGift } from 'app/shared/model/comment-of-gift.model';
import { CommentOfGiftService } from './comment-of-gift.service';

@Component({
  templateUrl: './comment-of-gift-delete-dialog.component.html'
})
export class CommentOfGiftDeleteDialogComponent {
  commentOfGift?: ICommentOfGift;

  constructor(
    protected commentOfGiftService: CommentOfGiftService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.commentOfGiftService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commentOfGiftListModification');
      this.activeModal.close();
    });
  }
}
