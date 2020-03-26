import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfGiftComponent } from 'app/entities/like-of-gift/like-of-gift.component';
import { LikeOfGiftService } from 'app/entities/like-of-gift/like-of-gift.service';
import { LikeOfGift } from 'app/shared/model/like-of-gift.model';

describe('Component Tests', () => {
  describe('LikeOfGift Management Component', () => {
    let comp: LikeOfGiftComponent;
    let fixture: ComponentFixture<LikeOfGiftComponent>;
    let service: LikeOfGiftService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfGiftComponent],
        providers: []
      })
        .overrideTemplate(LikeOfGiftComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(LikeOfGiftComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(LikeOfGiftService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new LikeOfGift('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.likeOfGifts && comp.likeOfGifts[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
