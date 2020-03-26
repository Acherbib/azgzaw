import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfCitationDetailComponent } from 'app/entities/like-of-citation/like-of-citation-detail.component';
import { LikeOfCitation } from 'app/shared/model/like-of-citation.model';

describe('Component Tests', () => {
  describe('LikeOfCitation Management Detail Component', () => {
    let comp: LikeOfCitationDetailComponent;
    let fixture: ComponentFixture<LikeOfCitationDetailComponent>;
    const route = ({ data: of({ likeOfCitation: new LikeOfCitation('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfCitationDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LikeOfCitationDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LikeOfCitationDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load likeOfCitation on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.likeOfCitation).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
