import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfStatusComponent } from 'app/entities/comment-of-status/comment-of-status.component';
import { CommentOfStatusService } from 'app/entities/comment-of-status/comment-of-status.service';
import { CommentOfStatus } from 'app/shared/model/comment-of-status.model';

describe('Component Tests', () => {
  describe('CommentOfStatus Management Component', () => {
    let comp: CommentOfStatusComponent;
    let fixture: ComponentFixture<CommentOfStatusComponent>;
    let service: CommentOfStatusService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfStatusComponent],
        providers: []
      })
        .overrideTemplate(CommentOfStatusComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommentOfStatusComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommentOfStatusService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommentOfStatus('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commentOfStatuses && comp.commentOfStatuses[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
