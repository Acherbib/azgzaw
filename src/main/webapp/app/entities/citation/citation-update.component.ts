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

import { ICitation, Citation } from 'app/shared/model/citation.model';
import { CitationService } from './citation.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { IUser } from 'app/core/user/user.model';
import { UserService } from 'app/core/user/user.service';

@Component({
  selector: 'jhi-citation-update',
  templateUrl: './citation-update.component.html'
})
export class CitationUpdateComponent implements OnInit {
  isSaving = false;

  users: IUser[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    greenCitationMedia: [],
    greenCitationMediaContentType: [],
    dateOfCreation: null,
    backgroundColor: [],
    greenCitationPrivacy: [null, [Validators.required]],
    authorOfCitation: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected eventManager: JhiEventManager,
    protected citationService: CitationService,
    protected userService: UserService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ citation }) => {
      this.updateForm(citation);

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

  updateForm(citation: ICitation): void {
    this.editForm.patchValue({
      id: citation.id,
      description: citation.description,
      greenCitationMedia: citation.greenCitationMedia,
      greenCitationMediaContentType: citation.greenCitationMediaContentType,
      dateOfCreation: moment().format(),
      backgroundColor: citation.backgroundColor,
      greenCitationPrivacy: citation.greenCitationPrivacy,
      authorOfCitation: citation.authorOfCitation
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
    const citation = this.createFromForm();
    if (citation.id !== undefined) {
      this.subscribeToSaveResponse(this.citationService.update(citation));
    } else {
      this.subscribeToSaveResponse(this.citationService.create(citation));
    }
  }

  private createFromForm(): ICitation {
    return {
      ...new Citation(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      greenCitationMediaContentType: this.editForm.get(['greenCitationMediaContentType'])!.value,
      greenCitationMedia: this.editForm.get(['greenCitationMedia'])!.value,
      dateOfCreation:
        this.editForm.get(['dateOfCreation'])!.value != null
          ? moment(moment().format(), DATE_TIME_FORMAT)
          : undefined,
      backgroundColor: this.editForm.get(['backgroundColor'])!.value,
      greenCitationPrivacy: this.editForm.get(['greenCitationPrivacy'])!.value,
      authorOfCitation: this.editForm.get(['authorOfCitation'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICitation>>): void {
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

  trackById(index: number, item: IUser): any {
    return item.id;
  }
}
