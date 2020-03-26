import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { CommentOfGiftComponent } from './comment-of-gift.component';
import { CommentOfGiftDetailComponent } from './comment-of-gift-detail.component';
import { CommentOfGiftUpdateComponent } from './comment-of-gift-update.component';
import { CommentOfGiftDeleteDialogComponent } from './comment-of-gift-delete-dialog.component';
import { commentOfGiftRoute } from './comment-of-gift.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(commentOfGiftRoute)],
  declarations: [CommentOfGiftComponent, CommentOfGiftDetailComponent, CommentOfGiftUpdateComponent, CommentOfGiftDeleteDialogComponent],
  entryComponents: [CommentOfGiftDeleteDialogComponent]
})
export class AzgzawCommentOfGiftModule {}
