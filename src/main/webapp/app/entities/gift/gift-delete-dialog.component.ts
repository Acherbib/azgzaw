import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IGift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';

@Component({
  templateUrl: './gift-delete-dialog.component.html'
})
export class GiftDeleteDialogComponent {
  gift?: IGift;

  constructor(protected giftService: GiftService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.giftService.delete(id).subscribe(() => {
      this.eventManager.broadcast('giftListModification');
      this.activeModal.close();
    });
  }
}
