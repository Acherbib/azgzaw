import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { ILikeOfCitation, LikeOfCitation } from 'app/shared/model/like-of-citation.model';
import { LikeOfCitationService } from './like-of-citation.service';
import { ICitation } from 'app/shared/model/citation.model';
import { CitationService } from 'app/entities/citation/citation.service';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

type SelectableEntity = ICitation | IUser;

@Component({
  selector: 'jhi-like-of-citation-update',
  templateUrl: './like-of-citation-update.component.html'
})
export class LikeOfCitationUpdateComponent implements OnInit {
  isSaving = false;

  citations: ICitation[] = [];

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    typeOfLike: [null, [Validators.required]],
    citation: [],
    authorOfLike: []
  });

  constructor(
    protected likeOfCitationService: LikeOfCitationService,
    protected citationService: CitationService,
    protected userService: UserService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ likeOfCitation }) => {
      this.updateForm(likeOfCitation);

      this.citationService
        .query({ filter: 'likeofcitation-is-null' })
        .pipe(
          map((res: HttpResponse<ICitation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICitation[]) => {
          if (!likeOfCitation.citation || !likeOfCitation.citation.id) {
            this.citations = resBody;
          } else {
            this.citationService
              .find(likeOfCitation.citation.id)
              .pipe(
                map((subRes: HttpResponse<ICitation>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: ICitation[]) => {
                this.citations = concatRes;
              });
          }
        });

      this.userService
        .query()
        .pipe(
          map((res: HttpResponse<IUser[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IUser[]) => (this.users = resBody));
    });
  }

  updateForm(likeOfCitation: ILikeOfCitation): void {
    this.editForm.patchValue({
      id: likeOfCitation.id,
      typeOfLike: likeOfCitation.typeOfLike,
      citation: likeOfCitation.citation,
      authorOfLike: likeOfCitation.authorOfLike
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const likeOfCitation = this.createFromForm();
    if (likeOfCitation.id !== undefined) {
      this.subscribeToSaveResponse(this.likeOfCitationService.update(likeOfCitation));
    } else {
      this.subscribeToSaveResponse(this.likeOfCitationService.create(likeOfCitation));
    }
  }

  private createFromForm(): ILikeOfCitation {
    return {
      ...new LikeOfCitation(),
      id: this.editForm.get(['id'])!.value,
      typeOfLike: this.editForm.get(['typeOfLike'])!.value,
      citation: this.editForm.get(['citation'])!.value,
      authorOfLike: this.editForm.get(['authorOfLike'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ILikeOfCitation>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
