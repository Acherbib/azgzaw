import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfStatusUpdateComponent } from 'app/entities/comment-of-status/comment-of-status-update.component';
import { CommentOfStatusService } from 'app/entities/comment-of-status/comment-of-status.service';
import { CommentOfStatus } from 'app/shared/model/comment-of-status.model';

describe('Component Tests', () => {
  describe('CommentOfStatus Management Update Component', () => {
    let comp: CommentOfStatusUpdateComponent;
    let fixture: ComponentFixture<CommentOfStatusUpdateComponent>;
    let service: CommentOfStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommentOfStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommentOfStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommentOfStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommentOfStatus('123');
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
        const entity = new CommentOfStatus();
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
