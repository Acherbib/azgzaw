import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AzgzawSharedModule } from 'app/shared/shared.module';
import { AzgzawCoreModule } from 'app/core/core.module';
import { AzgzawAppRoutingModule } from './app-routing.module';
import { AzgzawHomeModule } from './home/home.module';
import { AzgzawEntityModule } from './entities/entity.module';

// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';
import {AvatarModule} from "ngx-avatar";
import { NgxSpinnerModule } from "ngx-spinner";

@NgModule({
  imports: [
    BrowserModule,
    AzgzawSharedModule,
    AzgzawCoreModule,
    AzgzawHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AzgzawEntityModule,
    AzgzawAppRoutingModule,
    AvatarModule,
    NgxSpinnerModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class AzgzawAppModule {}
