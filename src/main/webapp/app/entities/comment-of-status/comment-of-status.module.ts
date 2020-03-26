import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { CommentOfStatusComponent } from './comment-of-status.component';
import { CommentOfStatusDetailComponent } from './comment-of-status-detail.component';
import { CommentOfStatusUpdateComponent } from './comment-of-status-update.component';
import { CommentOfStatusDeleteDialogComponent } from './comment-of-status-delete-dialog.component';
import { commentOfStatusRoute } from './comment-of-status.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(commentOfStatusRoute)],
  declarations: [
    CommentOfStatusComponent,
    CommentOfStatusDetailComponent,
    CommentOfStatusUpdateComponent,
    CommentOfStatusDeleteDialogComponent
  ],
  entryComponents: [CommentOfStatusDeleteDialogComponent]
})
export class AzgzawCommentOfStatusModule {}
