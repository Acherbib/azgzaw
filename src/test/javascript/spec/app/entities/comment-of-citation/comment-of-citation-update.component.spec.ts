import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfCitationUpdateComponent } from 'app/entities/comment-of-citation/comment-of-citation-update.component';
import { CommentOfCitationService } from 'app/entities/comment-of-citation/comment-of-citation.service';
import { CommentOfCitation } from 'app/shared/model/comment-of-citation.model';

describe('Component Tests', () => {
  describe('CommentOfCitation Management Update Component', () => {
    let comp: CommentOfCitationUpdateComponent;
    let fixture: ComponentFixture<CommentOfCitationUpdateComponent>;
    let service: CommentOfCitationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfCitationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommentOfCitationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommentOfCitationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommentOfCitationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommentOfCitation('123');
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommentOfCitation();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
