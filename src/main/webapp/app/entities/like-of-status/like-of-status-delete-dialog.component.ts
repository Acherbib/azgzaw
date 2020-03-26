import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILikeOfStatus } from 'app/shared/model/like-of-status.model';
import { LikeOfStatusService } from './like-of-status.service';

@Component({
  templateUrl: './like-of-status-delete-dialog.component.html'
})
export class LikeOfStatusDeleteDialogComponent {
  likeOfStatus?: ILikeOfStatus;

  constructor(
    protected likeOfStatusService: LikeOfStatusService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.likeOfStatusService.delete(id).subscribe(() => {
      this.eventManager.broadcast('likeOfStatusListModification');
      this.activeModal.close();
    });
  }
}
