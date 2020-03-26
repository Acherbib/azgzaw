import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfStatusComponent } from 'app/entities/like-of-status/like-of-status.component';
import { LikeOfStatusService } from 'app/entities/like-of-status/like-of-status.service';
import { LikeOfStatus } from 'app/shared/model/like-of-status.model';

describe('Component Tests', () => {
  describe('LikeOfStatus Management Component', () => {
    let comp: LikeOfStatusComponent;
    let fixture: ComponentFixture<LikeOfStatusComponent>;
    let service: LikeOfStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfStatusComponent],
        providers: []
      })
        .overrideTemplate(LikeOfStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeOfStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeOfStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LikeOfStatus('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.likeOfStatuses && comp.likeOfStatuses[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
