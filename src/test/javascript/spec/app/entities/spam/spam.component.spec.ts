import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AzgzawTestModule } from '../../../test.module';
import { SpamComponent } from 'app/entities/spam/spam.component';
import { SpamService } from 'app/entities/spam/spam.service';
import { Spam } from 'app/shared/model/spam.model';

describe('Component Tests', () => {
  describe('Spam Management Component', () => {
    let comp: SpamComponent;
    let fixture: ComponentFixture<SpamComponent>;
    let service: SpamService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AzgzawTestModule],
        declarations: [SpamComponent],
        providers: []
      })
        .overrideTemplate(SpamComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpamComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpamService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Spam('123')],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.spams && comp.spams[0]).toEqual(jasmine.objectContaining({ id: '123' }));
    });
  });
});
