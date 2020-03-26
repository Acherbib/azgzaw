import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AzgzawTestModule } from '../../../test.module';
import { SpamDetailComponent } from 'app/entities/spam/spam-detail.component';
import { Spam } from 'app/shared/model/spam.model';

describe('Component Tests', () => {
  describe('Spam Management Detail Component', () => {
    let comp: SpamDetailComponent;
    let fixture: ComponentFixture<SpamDetailComponent>;
    const route = ({ data: of({ spam: new Spam('123') }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [SpamDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpamDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpamDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load spam on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.spam).toEqual(jasmine.objectContaining({ id: '123' }));
      });
    });
  });
});
