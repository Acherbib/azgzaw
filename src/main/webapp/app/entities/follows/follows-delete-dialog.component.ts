import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFollows } from 'app/shared/model/follows.model';
import { FollowsService } from './follows.service';

@Component({
  templateUrl: './follows-delete-dialog.component.html'
})
export class FollowsDeleteDialogComponent {
  follows?: IFollows;

  constructor(protected followsService: FollowsService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.followsService.delete(id).subscribe(() => {
      this.eventManager.broadcast('followsListModification');
      this.activeModal.close();
    });
  }
}
