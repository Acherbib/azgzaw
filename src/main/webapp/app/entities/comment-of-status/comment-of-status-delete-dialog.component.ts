import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommentOfStatus } from 'app/shared/model/comment-of-status.model';
import { CommentOfStatusService } from './comment-of-status.service';

@Component({
  templateUrl: './comment-of-status-delete-dialog.component.html'
})
export class CommentOfStatusDeleteDialogComponent {
  commentOfStatus?: ICommentOfStatus;

  constructor(
    protected commentOfStatusService: CommentOfStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.commentOfStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commentOfStatusListModification');
      this.activeModal.close();
    });
  }
}
