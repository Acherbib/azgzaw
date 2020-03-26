import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommentOfCitation, CommentOfCitation } from 'app/shared/model/comment-of-citation.model';
import { CommentOfCitationService } from './comment-of-citation.service';
import { CommentOfCitationComponent } from './comment-of-citation.component';
import { CommentOfCitationDetailComponent } from './comment-of-citation-detail.component';
import { CommentOfCitationUpdateComponent } from './comment-of-citation-update.component';

@Injectable({ providedIn: 'root' })
export class CommentOfCitationResolve implements Resolve<ICommentOfCitation> {
  constructor(private service: CommentOfCitationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommentOfCitation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commentOfCitation: HttpResponse<CommentOfCitation>) => {
          if (commentOfCitation.body) {
            return of(commentOfCitation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommentOfCitation());
  }
}

export const commentOfCitationRoute: Routes = [
  {
    path: '',
    component: CommentOfCitationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommentOfCitationDetailComponent,
    resolve: {
      commentOfCitation: CommentOfCitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommentOfCitationUpdateComponent,
    resolve: {
      commentOfCitation: CommentOfCitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommentOfCitationUpdateComponent,
    resolve: {
      commentOfCitation: CommentOfCitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfCitation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
