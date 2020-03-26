import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiDataUtils, JhiFileLoadError, JhiEventManager, JhiEventWithContent } from 'ng-jhipster';

import { ICommentOfCitation, CommentOfCitation } from 'app/shared/model/comment-of-citation.model';
import { CommentOfCitationService } from './comment-of-citation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { ICitation } from 'app/shared/model/citation.model';
import { CitationService } from 'app/entities/citation/citation.service';

@Component({
  selector: 'jhi-comment-of-citation-update',
  templateUrl: './comment-of-citation-update.component.html'
})
export class CommentOfCitationUpdateComponent implements OnInit {
  isSaving = false;

  citations: ICitation[] = [];

  editForm = this.fb.group({
    id: [],
    bodyComment: [null, [Validators.required]],
    commentOfCitationImage: [],
    commentOfCitationImageContentType: [],
    dateOfCreation: [],
    citation: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected commentOfCitationService: CommentOfCitationService,
    protected citationService: CitationService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ commentOfCitation }) => {
      this.updateForm(commentOfCitation);

      this.citationService
        .query({ filter: 'commentofcitation-is-null' })
        .pipe(
          map((res: HttpResponse<ICitation[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ICitation[]) => {
          if (!commentOfCitation.citation || !commentOfCitation.citation.id) {
            this.citations = resBody;
          } else {
            this.citationService
              .find(commentOfCitation.citation.id)
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
    });
  }

  updateForm(commentOfCitation: ICommentOfCitation): void {
    this.editForm.patchValue({
      id: commentOfCitation.id,
      bodyComment: commentOfCitation.bodyComment,
      commentOfCitationImage: commentOfCitation.commentOfCitationImage,
      commentOfCitationImageContentType: commentOfCitation.commentOfCitationImageContentType,
      dateOfCreation: commentOfCitation.dateOfCreation != null ? commentOfCitation.dateOfCreation.format(DATE_TIME_FORMAT) : null,
      citation: commentOfCitation.citation
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(contentType: string, base64String: string): void {
    this.dataUtils.openFile(contentType, base64String);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe(null, (err: JhiFileLoadError) => {
      this.eventManager.broadcast(
        new JhiEventWithContent<AlertError>('azgzawApp.error', { ...err, key: 'error.file.' + err.key })
      );
    });
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const commentOfCitation = this.createFromForm();
    if (commentOfCitation.id !== undefined) {
      this.subscribeToSaveResponse(this.commentOfCitationService.update(commentOfCitation));
    } else {
      this.subscribeToSaveResponse(this.commentOfCitationService.create(commentOfCitation));
    }
  }

  private createFromForm(): ICommentOfCitation {
    return {
      ...new CommentOfCitation(),
      id: this.editForm.get(['id'])!.value,
      bodyComment: this.editForm.get(['bodyComment'])!.value,
      commentOfCitationImageContentType: this.editForm.get(['commentOfCitationImageContentType'])!.value,
      commentOfCitationImage: this.editForm.get(['commentOfCitationImage'])!.value,
      dateOfCreation:
        this.editForm.get(['dateOfCreation'])!.value != null
          ? moment(this.editForm.get(['dateOfCreation'])!.value, DATE_TIME_FORMAT)
          : undefined,
      citation: this.editForm.get(['citation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommentOfCitation>>): void {
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
