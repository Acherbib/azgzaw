import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILikeOfCitation } from 'app/shared/model/like-of-citation.model';

@Component({
  selector: 'jhi-like-of-citation-detail',
  templateUrl: './like-of-citation-detail.component.html'
})
export class LikeOfCitationDetailComponent implements OnInit {
  likeOfCitation: ILikeOfCitation | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likeOfCitation }) => {
      this.likeOfCitation = likeOfCitation;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
