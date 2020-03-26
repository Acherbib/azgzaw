import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICitation } from 'app/shared/model/citation.model';
import { CitationService } from './citation.service';

@Component({
  templateUrl: './citation-delete-dialog.component.html'
})
export class CitationDeleteDialogComponent {
  citation?: ICitation;

  constructor(protected citationService: CitationService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: string): void {
    this.citationService.delete(id).subscribe(() => {
      this.eventManager.broadcast('citationListModification');
      this.activeModal.close();
    });
  }
}
