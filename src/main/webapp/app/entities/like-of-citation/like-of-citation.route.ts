import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ILikeOfCitation, LikeOfCitation } from 'app/shared/model/like-of-citation.model';
import { LikeOfCitationService } from './like-of-citation.service';
import { LikeOfCitationComponent } from './like-of-citation.component';
import { LikeOfCitationDetailComponent } from './like-of-citation-detail.component';
import { LikeOfCitationUpdateComponent } from './like-of-citation-update.component';

@Injectable({ providedIn: 'root' })
export class LikeOfCitationResolve implements Resolve<ILikeOfCitation> {
  constructor(private service: LikeOfCitationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ILikeOfCitation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((likeOfCitation: HttpResponse<LikeOfCitation>) => {
          if (likeOfCitation.body) {
            return of(likeOfCitation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new LikeOfCitation());
  }
}

export const likeOfCitationRoute: Routes = [
  {
    path: '',
    component: LikeOfCitationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: LikeOfCitationDetailComponent,
    resolve: {
      likeOfCitation: LikeOfCitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: LikeOfCitationUpdateComponent,
    resolve: {
      likeOfCitation: LikeOfCitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: LikeOfCitationUpdateComponent,
    resolve: {
      likeOfCitation: LikeOfCitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.likeOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
