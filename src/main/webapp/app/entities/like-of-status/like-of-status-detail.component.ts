import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILikeOfStatus } from 'app/shared/model/like-of-status.model';

@Component({
  selector: 'jhi-like-of-status-detail',
  templateUrl: './like-of-status-detail.component.html'
})
export class LikeOfStatusDetailComponent implements OnInit {
  likeOfStatus: ILikeOfStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likeOfStatus }) => {
      this.likeOfStatus = likeOfStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
