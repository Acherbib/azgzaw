import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILikeOfCitation } from 'app/shared/model/like-of-citation.model';
import { LikeOfCitationService } from './like-of-citation.service';

@Component({
  templateUrl: './like-of-citation-delete-dialog.component.html'
})
export class LikeOfCitationDeleteDialogComponent {
  likeOfCitation?: ILikeOfCitation;

  constructor(
    protected likeOfCitationService: LikeOfCitationService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.likeOfCitationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('likeOfCitationListModification');
      this.activeModal.close();
    });
  }
}
