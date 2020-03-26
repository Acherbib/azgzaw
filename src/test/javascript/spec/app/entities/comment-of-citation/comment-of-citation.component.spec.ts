import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfCitationComponent } from 'app/entities/comment-of-citation/comment-of-citation.component';
import { CommentOfCitationService } from 'app/entities/comment-of-citation/comment-of-citation.service';
import { CommentOfCitation } from 'app/shared/model/comment-of-citation.model';

describe('Component Tests', () => {
  describe('CommentOfCitation Management Component', () => {
    let comp: CommentOfCitationComponent;
    let fixture: ComponentFixture<CommentOfCitationComponent>;
    let service: CommentOfCitationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfCitationComponent],
        providers: []
      })
        .overrideTemplate(CommentOfCitationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommentOfCitationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommentOfCitationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommentOfCitation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commentOfCitations && comp.commentOfCitations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
