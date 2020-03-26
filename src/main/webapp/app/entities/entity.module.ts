import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'auction',
        loadChildren: () => import('./auction/auction.module').then(m => m.AzgzawAuctionModule)
      },
      {
        path: 'gift',
        loadChildren: () => import('./gift/gift.module').then(m => m.AzgzawGiftModule)
      },
      {
        path: 'profile',
        loadChildren: () => import('./profile/profile.module').then(m => m.AzgzawProfileModule)
      },
      {
        path: 'status',
        loadChildren: () => import('./status/status.module').then(m => m.AzgzawStatusModule)
      },
      {
        path: 'citation',
        loadChildren: () => import('./citation/citation.module').then(m => m.AzgzawCitationModule)
      },
      {
        path: 'spam',
        loadChildren: () => import('./spam/spam.module').then(m => m.AzgzawSpamModule)
      },
      {
        path: 'follows',
        loadChildren: () => import('./follows/follows.module').then(m => m.AzgzawFollowsModule)
      },
      {
        path: 'message',
        loadChildren: () => import('./message/message.module').then(m => m.AzgzawMessageModule)
      },
      {
        path: 'comment-of-citation',
        loadChildren: () => import('./comment-of-citation/comment-of-citation.module').then(m => m.AzgzawCommentOfCitationModule)
      },
      {
        path: 'comment-of-gift',
        loadChildren: () => import('./comment-of-gift/comment-of-gift.module').then(m => m.AzgzawCommentOfGiftModule)
      },
      {
        path: 'comment-of-status',
        loadChildren: () => import('./comment-of-status/comment-of-status.module').then(m => m.AzgzawCommentOfStatusModule)
      },
      {
        path: 'country',
        loadChildren: () => import('./country/country.module').then(m => m.AzgzawCountryModule)
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.AzgzawLocationModule)
      },
      {
        path: 'like-of-citation',
        loadChildren: () => import('./like-of-citation/like-of-citation.module').then(m => m.AzgzawLikeOfCitationModule)
      },
      {
        path: 'like-of-status',
        loadChildren: () => import('./like-of-status/like-of-status.module').then(m => m.AzgzawLikeOfStatusModule)
      },
      {
        path: 'like-of-gift',
        loadChildren: () => import('./like-of-gift/like-of-gift.module').then(m => m.AzgzawLikeOfGiftModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AzgzawEntityModule {}
