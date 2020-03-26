import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfCitationUpdateComponent } from 'app/entities/like-of-citation/like-of-citation-update.component';
import { LikeOfCitationService } from 'app/entities/like-of-citation/like-of-citation.service';
import { LikeOfCitation } from 'app/shared/model/like-of-citation.model';

describe('Component Tests', () => {
  describe('LikeOfCitation Management Update Component', () => {
    let comp: LikeOfCitationUpdateComponent;
    let fixture: ComponentFixture<LikeOfCitationUpdateComponent>;
    let service: LikeOfCitationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfCitationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LikeOfCitationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeOfCitationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeOfCitationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LikeOfCitation('123');
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
        const entity = new LikeOfCitation();
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
