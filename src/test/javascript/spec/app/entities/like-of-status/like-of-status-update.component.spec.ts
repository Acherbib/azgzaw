import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfStatusUpdateComponent } from 'app/entities/like-of-status/like-of-status-update.component';
import { LikeOfStatusService } from 'app/entities/like-of-status/like-of-status.service';
import { LikeOfStatus } from 'app/shared/model/like-of-status.model';

describe('Component Tests', () => {
  describe('LikeOfStatus Management Update Component', () => {
    let comp: LikeOfStatusUpdateComponent;
    let fixture: ComponentFixture<LikeOfStatusUpdateComponent>;
    let service: LikeOfStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfStatusUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(LikeOfStatusUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeOfStatusUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeOfStatusService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new LikeOfStatus('123');
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
        const entity = new LikeOfStatus();
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
