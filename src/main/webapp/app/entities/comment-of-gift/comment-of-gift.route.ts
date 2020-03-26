import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICommentOfGift, CommentOfGift } from 'app/shared/model/comment-of-gift.model';
import { CommentOfGiftService } from './comment-of-gift.service';
import { CommentOfGiftComponent } from './comment-of-gift.component';
import { CommentOfGiftDetailComponent } from './comment-of-gift-detail.component';
import { CommentOfGiftUpdateComponent } from './comment-of-gift-update.component';

@Injectable({ providedIn: 'root' })
export class CommentOfGiftResolve implements Resolve<ICommentOfGift> {
  constructor(private service: CommentOfGiftService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICommentOfGift> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((commentOfGift: HttpResponse<CommentOfGift>) => {
          if (commentOfGift.body) {
            return of(commentOfGift.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CommentOfGift());
  }
}

export const commentOfGiftRoute: Routes = [
  {
    path: '',
    component: CommentOfGiftComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CommentOfGiftDetailComponent,
    resolve: {
      commentOfGift: CommentOfGiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CommentOfGiftUpdateComponent,
    resolve: {
      commentOfGift: CommentOfGiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CommentOfGiftUpdateComponent,
    resolve: {
      commentOfGift: CommentOfGiftResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.commentOfGift.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
