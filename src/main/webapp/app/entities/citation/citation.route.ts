import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICitation, Citation } from 'app/shared/model/citation.model';
import { CitationService } from './citation.service';
import { CitationComponent } from './citation.component';
import { CitationDetailComponent } from './citation-detail.component';
import { CitationUpdateComponent } from './citation-update.component';

@Injectable({ providedIn: 'root' })
export class CitationResolve implements Resolve<ICitation> {
  constructor(private service: CitationService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICitation> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((citation: HttpResponse<Citation>) => {
          if (citation.body) {
            return of(citation.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Citation());
  }
}

export const citationRoute: Routes = [
  {
    path: '',
    component: CitationComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.citation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CitationDetailComponent,
    resolve: {
      citation: CitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.citation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CitationUpdateComponent,
    resolve: {
      citation: CitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.citation.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CitationUpdateComponent,
    resolve: {
      citation: CitationResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'azgzawApp.citation.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
