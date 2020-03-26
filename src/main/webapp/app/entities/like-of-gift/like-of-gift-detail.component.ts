import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ILikeOfGift } from 'app/shared/model/like-of-gift.model';

@Component({
  selector: 'jhi-like-of-gift-detail',
  templateUrl: './like-of-gift-detail.component.html'
})
export class LikeOfGiftDetailComponent implements OnInit {
  likeOfGift: ILikeOfGift | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likeOfGift }) => {
      this.likeOfGift = likeOfGift;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
