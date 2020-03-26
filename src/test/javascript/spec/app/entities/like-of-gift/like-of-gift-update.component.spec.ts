import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfGiftUpdateComponent } from 'app/entities/like-of-gift/like-of-gift-update.component';
import { LikeOfGiftService } from 'app/entities/like-of-gift/like-of-gift.service';
import { LikeOfGift } from 'app/shared/model/like-of-gift.model';

describe('Component Tests', () => {
  describe('LikeOfGift Management Update Component', () => {
    let comp: LikeOfGiftUpdateComponent;
    let fixture: ComponentFixture<LikeOfGiftUpdateComponent>;
    let service: LikeOfGiftService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfGiftUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LikeOfGiftUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeOfGiftUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeOfGiftService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LikeOfGift('123');
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
        const entity = new LikeOfGift();
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
