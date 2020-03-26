import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfGiftDetailComponent } from 'app/entities/like-of-gift/like-of-gift-detail.component';
import { LikeOfGift } from 'app/shared/model/like-of-gift.model';

describe('Component Tests', () => {
  describe('LikeOfGift Management Detail Component', () => {
    let comp: LikeOfGiftDetailComponent;
    let fixture: ComponentFixture<LikeOfGiftDetailComponent>;
    const route = ({ data: of({ likeOfGift: new LikeOfGift('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfGiftDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LikeOfGiftDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LikeOfGiftDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load likeOfGift on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.likeOfGift).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
