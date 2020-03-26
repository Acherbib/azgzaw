import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { AzgzawTestModule } from '../../../test.module';
import { CommentOfCitationDetailComponent } from 'app/entities/comment-of-citation/comment-of-citation-detail.component';
import { CommentOfCitation } from 'app/shared/model/comment-of-citation.model';

describe('Component Tests', () => {
  describe('CommentOfCitation Management Detail Component', () => {
    let comp: CommentOfCitationDetailComponent;
    let fixture: ComponentFixture<CommentOfCitationDetailComponent>;
    let dataUtils: JhiDataUtils;
    const route = ({ data: of({ commentOfCitation: new CommentOfCitation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [CommentOfCitationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(CommentOfCitationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CommentOfCitationDetailComponent);
      comp = fixture.componentInstance;
      dataUtils = fixture.debugElement.injector.get(JhiDataUtils);
    });

    describe('OnInit', () => {
      it('Should load commentOfCitation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.commentOfCitation).toEqual(jasmine.objectContaining({ id: '123' }));
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
