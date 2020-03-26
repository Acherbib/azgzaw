import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { FollowsUpdateComponent } from 'app/entities/follows/follows-update.component';
import { FollowsService } from 'app/entities/follows/follows.service';
import { Follows } from 'app/shared/model/follows.model';

describe('Component Tests', () => {
  describe('Follows Management Update Component', () => {
    let comp: FollowsUpdateComponent;
    let fixture: ComponentFixture<FollowsUpdateComponent>;
    let service: FollowsService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [FollowsUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FollowsUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FollowsUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FollowsService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Follows('123');
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
        const entity = new Follows();
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
