import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILikeOfStatus, LikeOfStatus } from 'app/shared/model/like-of-status.model';
import { LikeOfStatusService } from './like-of-status.service';
import { LikeOfStatusComponent } from './like-of-status.component';
import { LikeOfStatusDetailComponent } from './like-of-status-detail.component';
import { LikeOfStatusUpdateComponent } from './like-of-status-update.component';

@Injectable({ providedIn: 'root' })
export class LikeOfStatusResolve implements Resolve<ILikeOfStatus> {
  constructor(private service: LikeOfStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILikeOfStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((likeOfStatus: HttpResponse<LikeOfStatus>) => {
          if (likeOfStatus.body) {
            return of(likeOfStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LikeOfStatus());
  }
}

export const likeOfStatusRoute: Routes = [
  {
    path: '',
    component: LikeOfStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LikeOfStatusDetailComponent,
    resolve: {
      likeOfStatus: LikeOfStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LikeOfStatusUpdateComponent,
    resolve: {
      likeOfStatus: LikeOfStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LikeOfStatusUpdateComponent,
    resolve: {
      likeOfStatus: LikeOfStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
