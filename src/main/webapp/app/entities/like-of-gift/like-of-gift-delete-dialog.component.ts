import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ILikeOfGift } from 'app/shared/model/like-of-gift.model';
import { LikeOfGiftService } from './like-of-gift.service';

@Component({
  templateUrl: './like-of-gift-delete-dialog.component.html'
})
export class LikeOfGiftDeleteDialogComponent {
  likeOfGift?: ILikeOfGift;

  constructor(
    protected likeOfGiftService: LikeOfGiftService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.likeOfGiftService.delete(id).subscribe(() => {
      this.eventManager.broadcast('likeOfGiftListModification');
      this.activeModal.close();
    });
  }
}
