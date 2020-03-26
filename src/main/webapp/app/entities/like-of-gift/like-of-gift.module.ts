import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { LikeOfGiftComponent } from './like-of-gift.component';
import { LikeOfGiftDetailComponent } from './like-of-gift-detail.component';
import { LikeOfGiftUpdateComponent } from './like-of-gift-update.component';
import { LikeOfGiftDeleteDialogComponent } from './like-of-gift-delete-dialog.component';
import { likeOfGiftRoute } from './like-of-gift.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(likeOfGiftRoute)],
  declarations: [LikeOfGiftComponent, LikeOfGiftDetailComponent, LikeOfGiftUpdateComponent, LikeOfGiftDeleteDialogComponent],
  entryComponents: [LikeOfGiftDeleteDialogComponent]
})
export class AzgzawLikeOfGiftModule {}
