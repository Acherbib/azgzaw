import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpam } from 'app/shared/model/spam.model';
import { SpamService } from './spam.service';

@Component({
  templateUrl: './spam-delete-dialog.component.html'
})
export class SpamDeleteDialogComponent {
  spam?: ISpam;

  constructor(protected spamService: SpamService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.spamService.delete(id).subscribe(() => {
      this.eventManager.broadcast('spamListModification');
      this.activeModal.close();
    });
  }
}
