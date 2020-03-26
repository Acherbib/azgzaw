import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { CitationComponent } from './citation.component';
import { CitationDetailComponent } from './citation-detail.component';
import { CitationUpdateComponent } from './citation-update.component';
import { CitationDeleteDialogComponent } from './citation-delete-dialog.component';
import { citationRoute } from './citation.route';
import {AvatarModule} from "ngx-avatar";

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(citationRoute),AvatarModule],
  declarations: [CitationComponent, CitationDetailComponent, CitationUpdateComponent, CitationDeleteDialogComponent],
  entryComponents: [CitationDeleteDialogComponent]
})
export class AzgzawCitationModule {}
