import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { CommentOfCitationComponent } from './comment-of-citation.component';
import { CommentOfCitationDetailComponent } from './comment-of-citation-detail.component';
import { CommentOfCitationUpdateComponent } from './comment-of-citation-update.component';
import { CommentOfCitationDeleteDialogComponent } from './comment-of-citation-delete-dialog.component';
import { commentOfCitationRoute } from './comment-of-citation.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(commentOfCitationRoute)],
  declarations: [
    CommentOfCitationComponent,
    CommentOfCitationDetailComponent,
    CommentOfCitationUpdateComponent,
    CommentOfCitationDeleteDialogComponent
  ],
  entryComponents: [CommentOfCitationDeleteDialogComponent]
})
export class AzgzawCommentOfCitationModule {}
