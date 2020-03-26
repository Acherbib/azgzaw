import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { FollowsDetailComponent } from 'app/entities/follows/follows-detail.component';
import { Follows } from 'app/shared/model/follows.model';

describe('Component Tests', () => {
  describe('Follows Management Detail Component', () => {
    let comp: FollowsDetailComponent;
    let fixture: ComponentFixture<FollowsDetailComponent>;
    const route = ({ data: of({ follows: new Follows('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [FollowsDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(FollowsDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FollowsDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load follows on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.follows).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
