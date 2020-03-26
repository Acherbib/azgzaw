import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ISpam, Spam } from 'app/shared/model/spam.model';
import { SpamService } from './spam.service';
import { ICitation } from 'app/shared/model/citation.model';
import { CitationService } from 'app/entities/citation/citation.service';

@Component({
  selector: 'jhi-spam-update',
  templateUrl: './spam-update.component.html'
})
export class SpamUpdateComponent implements OnInit {
  isSaving = false;

  citations: ICitation[] = [];

  editForm = this.fb.group({
    id: [],
    cause: [null, [Validators.required]],
    dateOfCreation: [null, [Validators.required]],
    citation: []
  });

  constructor(
    protected spamService: SpamService,
    protected citationService: CitationService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ spam }) => {
      this.updateForm(spam);

      this.citationService
        .query()
        .pipe(
          map((res: HttpResponse<ICitation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICitation[]) => (this.citations = resBody));
    });
  }

  updateForm(spam: ISpam): void {
    this.editForm.patchValue({
      id: spam.id,
      cause: spam.cause,
      dateOfCreation: spam.dateOfCreation != null ? spam.dateOfCreation.format(DATE_TIME_FORMAT) : null,
      citation: spam.citation
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const spam = this.createFromForm();
    if (spam.id !== undefined) {
      this.subscribeToSaveResponse(this.spamService.update(spam));
    } else {
      this.subscribeToSaveResponse(this.spamService.create(spam));
    }
  }

  private createFromForm(): ISpam {
    return {
      ...new Spam(),
      id: this.editForm.get(['id'])!.value,
      cause: this.editForm.get(['cause'])!.value,
      dateOfCreation:
        this.editForm.get(['dateOfCreation'])!.value != null
          ? moment(this.editForm.get(['dateOfCreation'])!.value, DATE_TIME_FORMAT)
          : undefined,
      citation: this.editForm.get(['citation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpam>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: ICitation): any {
    return item.id;
  }
}
