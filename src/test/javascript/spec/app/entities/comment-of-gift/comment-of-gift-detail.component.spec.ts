import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfGiftDetailComponent } from 'app/entities/comment-of-gift/comment-of-gift-detail.component';
import { CommentOfGift } from 'app/shared/model/comment-of-gift.model';

describe('Component Tests', () => {
  describe('CommentOfGift Management Detail Component', () => {
    let comp: CommentOfGiftDetailComponent;
    let fixture: ComponentFixture<CommentOfGiftDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ commentOfGift: new CommentOfGift('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfGiftDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommentOfGiftDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommentOfGiftDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load commentOfGift on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commentOfGift).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });

    describe('byteSize', () => {
      it('Should call byteSize from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'byteSize');
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.byteSize(fakeBase64);

        // THEN
        expect(dataUtils.byteSize).toBeCalledWith(fakeBase64);
      });
    });

    describe('openFile', () => {
      it('Should call openFile from JhiDataUtils', () => {
        // GIVEN
        spyOn(dataUtils, 'openFile');
        const fakeContentType = 'fake content type';
        const fakeBase64 = 'fake base64';

        // WHEN
        comp.openFile(fakeContentType, fakeBase64);

        // THEN
        expect(dataUtils.openFile).toBeCalledWith(fakeContentType, fakeBase64);
      });
    });
  });
});
