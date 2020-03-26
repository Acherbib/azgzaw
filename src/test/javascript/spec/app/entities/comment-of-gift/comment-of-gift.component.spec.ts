import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfGiftComponent } from 'app/entities/comment-of-gift/comment-of-gift.component';
import { CommentOfGiftService } from 'app/entities/comment-of-gift/comment-of-gift.service';
import { CommentOfGift } from 'app/shared/model/comment-of-gift.model';

describe('Component Tests', () => {
  describe('CommentOfGift Management Component', () => {
    let comp: CommentOfGiftComponent;
    let fixture: ComponentFixture<CommentOfGiftComponent>;
    let service: CommentOfGiftService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfGiftComponent],
        providers: []
      })
        .overrideTemplate(CommentOfGiftComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CommentOfGiftComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CommentOfGiftService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CommentOfGift('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.commentOfGifts && comp.commentOfGifts[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
