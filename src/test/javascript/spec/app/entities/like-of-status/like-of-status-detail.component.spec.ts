import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { LikeOfStatusDetailComponent } from 'app/entities/like-of-status/like-of-status-detail.component';
import { LikeOfStatus } from 'app/shared/model/like-of-status.model';

describe('Component Tests', () => {
  describe('LikeOfStatus Management Detail Component', () => {
    let comp: LikeOfStatusDetailComponent;
    let fixture: ComponentFixture<LikeOfStatusDetailComponent>;
    const route = ({ data: of({ likeOfStatus: new LikeOfStatus('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [LikeOfStatusDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(LikeOfStatusDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(LikeOfStatusDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load likeOfStatus on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.likeOfStatus).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
