import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { LikeOfCitationComponent } from './like-of-citation.component';
import { LikeOfCitationDetailComponent } from './like-of-citation-detail.component';
import { LikeOfCitationUpdateComponent } from './like-of-citation-update.component';
import { LikeOfCitationDeleteDialogComponent } from './like-of-citation-delete-dialog.component';
import { likeOfCitationRoute } from './like-of-citation.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(likeOfCitationRoute)],
  declarations: [
    LikeOfCitationComponent,
    LikeOfCitationDetailComponent,
    LikeOfCitationUpdateComponent,
    LikeOfCitationDeleteDialogComponent
  ],
  entryComponents: [LikeOfCitationDeleteDialogComponent]
})
export class AzgzawLikeOfCitationModule {}
