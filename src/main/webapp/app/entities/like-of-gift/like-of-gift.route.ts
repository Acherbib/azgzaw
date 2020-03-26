import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILikeOfGift, LikeOfGift } from 'app/shared/model/like-of-gift.model';
import { LikeOfGiftService } from './like-of-gift.service';
import { LikeOfGiftComponent } from './like-of-gift.component';
import { LikeOfGiftDetailComponent } from './like-of-gift-detail.component';
import { LikeOfGiftUpdateComponent } from './like-of-gift-update.component';

@Injectable({ providedIn: 'root' })
export class LikeOfGiftResolve implements Resolve<ILikeOfGift> {
  constructor(private service: LikeOfGiftService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILikeOfGift> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((likeOfGift: HttpResponse<LikeOfGift>) => {
          if (likeOfGift.body) {
            return of(likeOfGift.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LikeOfGift());
  }
}

export const likeOfGiftRoute: Routes = [
  {
    path: '',
    component: LikeOfGiftComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LikeOfGiftDetailComponent,
    resolve: {
      likeOfGift: LikeOfGiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LikeOfGiftUpdateComponent,
    resolve: {
      likeOfGift: LikeOfGiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LikeOfGiftUpdateComponent,
    resolve: {
      likeOfGift: LikeOfGiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
