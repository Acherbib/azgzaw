import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AzgzawSharedModule } from 'app/shared/shared.module';
import { GiftComponent } from './gift.component';
import { GiftDetailComponent } from './gift-detail.component';
import { GiftUpdateComponent } from './gift-update.component';
import { GiftDeleteDialogComponent } from './gift-delete-dialog.component';
import { giftRoute } from './gift.route';

@NgModule({
  imports: [AzgzawSharedModule, RouterModule.forChild(giftRoute)],
  declarations: [GiftComponent, GiftDetailComponent, GiftUpdateComponent, GiftDeleteDialogComponent],
  entryComponents: [GiftDeleteDialogComponent]
})
export class AzgzawGiftModule {}
