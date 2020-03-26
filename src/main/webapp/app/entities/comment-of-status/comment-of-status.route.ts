import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommentOfStatus, CommentOfStatus } from 'app/shared/model/comment-of-status.model';
import { CommentOfStatusService } from './comment-of-status.service';
import { CommentOfStatusComponent } from './comment-of-status.component';
import { CommentOfStatusDetailComponent } from './comment-of-status-detail.component';
import { CommentOfStatusUpdateComponent } from './comment-of-status-update.component';

@Injectable({ providedIn: 'root' })
export class CommentOfStatusResolve implements Resolve<ICommentOfStatus> {
  constructor(private service: CommentOfStatusService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommentOfStatus> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commentOfStatus: HttpResponse<CommentOfStatus>) => {
          if (commentOfStatus.body) {
            return of(commentOfStatus.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommentOfStatus());
  }
}

export const commentOfStatusRoute: Routes = [
  {
    path: '',
    component: CommentOfStatusComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommentOfStatusDetailComponent,
    resolve: {
      commentOfStatus: CommentOfStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommentOfStatusUpdateComponent,
    resolve: {
      commentOfStatus: CommentOfStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommentOfStatusUpdateComponent,
    resolve: {
      commentOfStatus: CommentOfStatusResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfStatus.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
