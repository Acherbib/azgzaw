import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfGiftUpdateComponent } from 'app/entities/comment-of-gift/comment-of-gift-update.component';
import { CommentOfGiftService } from 'app/entities/comment-of-gift/comment-of-gift.service';
import { CommentOfGift } from 'app/shared/model/comment-of-gift.model';

describe('Component Tests', () => {
  describe('CommentOfGift Management Update Component', () => {
    let comp: CommentOfGiftUpdateComponent;
    let fixture: ComponentFixture<CommentOfGiftUpdateComponent>;
    let service: CommentOfGiftService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfGiftUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CommentOfGiftUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommentOfGiftUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommentOfGiftService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new CommentOfGift('123');
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
        const entity = new CommentOfGift();
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
