import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { LikeOfStatusComponent } from './like-of-status.component';
import { LikeOfStatusDetailComponent } from './like-of-status-detail.component';
import { LikeOfStatusUpdateComponent } from './like-of-status-update.component';
import { LikeOfStatusDeleteDialogComponent } from './like-of-status-delete-dialog.component';
import { likeOfStatusRoute } from './like-of-status.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(likeOfStatusRoute)],
  declarations: [LikeOfStatusComponent, LikeOfStatusDetailComponent, LikeOfStatusUpdateComponent, LikeOfStatusDeleteDialogComponent],
  entryComponents: [LikeOfStatusDeleteDialogComponent]
})
export class AzgzawLikeOfStatusModule {}
