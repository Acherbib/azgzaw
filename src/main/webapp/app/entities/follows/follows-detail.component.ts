import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFollows } from 'app/shared/model/follows.model';

@Component({
  selector: 'jhi-follows-detail',
  templateUrl: './follows-detail.component.html'
})
export class FollowsDetailComponent implements OnInit {
  follows: IFollows | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ follows }) => {
      this.follows = follows;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
