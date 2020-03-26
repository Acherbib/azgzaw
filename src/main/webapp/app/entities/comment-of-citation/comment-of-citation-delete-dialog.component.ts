import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommentOfCitation } from 'app/shared/model/comment-of-citation.model';
import { CommentOfCitationService } from './comment-of-citation.service';

@Component({
  templateUrl: './comment-of-citation-delete-dialog.component.html'
})
export class CommentOfCitationDeleteDialogComponent {
  commentOfCitation?: ICommentOfCitation;

  constructor(
    protected commentOfCitationService: CommentOfCitationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.commentOfCitationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('commentOfCitationListModification');
      this.activeModal.close();
    });
  }
}
