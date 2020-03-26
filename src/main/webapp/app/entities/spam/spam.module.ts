import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { SpamComponent } from './spam.component';
import { SpamDetailComponent } from './spam-detail.component';
import { SpamUpdateComponent } from './spam-update.component';
import { SpamDeleteDialogComponent } from './spam-delete-dialog.component';
import { spamRoute } from './spam.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(spamRoute)],
  declarations: [SpamComponent, SpamDetailComponent, SpamUpdateComponent, SpamDeleteDialogComponent],
  entryComponents: [SpamDeleteDialogComponent]
})
export class AzgzawSpamModule {}
