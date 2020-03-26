import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IGift, Gift } from 'app/shared/model/gift.model';
import { GiftService } from './gift.service';
import { GiftComponent } from './gift.component';
import { GiftDetailComponent } from './gift-detail.component';
import { GiftUpdateComponent } from './gift-update.component';

@Injectable({ providedIn: 'root' })
export class GiftResolve implements Resolve<IGift> {
  constructor(private service: GiftService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IGift> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((gift: HttpResponse<Gift>) => {
          if (gift.body) {
            return of(gift.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Gift());
  }
}

export const giftRoute: Routes = [
  {
    path: '',
    component: GiftComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: GiftDetailComponent,
    resolve: {
      gift: GiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: GiftUpdateComponent,
    resolve: {
      gift: GiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: GiftUpdateComponent,
    resolve: {
      gift: GiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.gift.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
