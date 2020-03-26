import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpam } from 'app/shared/model/spam.model';

@Component({
  selector: 'jhi-spam-detail',
  templateUrl: './spam-detail.component.html'
})
export class SpamDetailComponent implements OnInit {
  spam: ISpam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spam }) => {
      this.spam = spam;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
