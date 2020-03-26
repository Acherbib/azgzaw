import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfCitationComponent } from 'app/entities/like-of-citation/like-of-citation.component';
import { LikeOfCitationService } from 'app/entities/like-of-citation/like-of-citation.service';
import { LikeOfCitation } from 'app/shared/model/like-of-citation.model';

describe('Component Tests', () => {
  describe('LikeOfCitation Management Component', () => {
    let comp: LikeOfCitationComponent;
    let fixture: ComponentFixture<LikeOfCitationComponent>;
    let service: LikeOfCitationService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfCitationComponent],
        providers: []
      })
        .overrideTemplate(LikeOfCitationComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeOfCitationComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeOfCitationService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LikeOfCitation('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.likeOfCitations && comp.likeOfCitations[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
