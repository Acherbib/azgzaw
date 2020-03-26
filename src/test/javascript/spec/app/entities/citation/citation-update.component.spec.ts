import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { CitationUpdateComponent } from 'app/entities/citation/citation-update.component';
import { CitationService } from 'app/entities/citation/citation.service';
import { Citation } from 'app/shared/model/citation.model';

describe('Component Tests', () => {
  describe('Citation Management Update Component', () => {
    let comp: CitationUpdateComponent;
    let fixture: ComponentFixture<CitationUpdateComponent>;
    let service: CitationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CitationUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(CitationUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CitationUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CitationService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Citation('123');
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
        const entity = new Citation();
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
