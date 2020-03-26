import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { SpamUpdateComponent } from 'app/entities/spam/spam-update.component';
import { SpamService } from 'app/entities/spam/spam.service';
import { Spam } from 'app/shared/model/spam.model';

describe('Component Tests', () => {
  describe('Spam Management Update Component', () => {
    let comp: SpamUpdateComponent;
    let fixture: ComponentFixture<SpamUpdateComponent>;
    let service: SpamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [SpamUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpamUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpamUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpamService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Spam('123');
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
        const entity = new Spam();
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
