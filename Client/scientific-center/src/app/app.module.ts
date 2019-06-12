import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {HomePageComponent} from './components/home-page/home-page.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { UploadPaperComponent } from './components/upload-paper/upload-paper.component';
import { JournalsComponent } from './components/journals/journals.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ToastrModule} from 'ngx-toastr';
import { TasksComponent } from './components/tasks/tasks.component';
import {NgSelectModule} from '@ng-select/ng-select';
import { ReviewerComponent } from './components/reviewer/reviewer.component';
import { ReviewComponent } from './components/review/review.component';
import { EditorDecisionComponent } from './components/editor-decision/editor-decision.component';

@NgModule({
  declarations: [
    AppComponent,
    WelcomeComponent,
    HomePageComponent,
    NavbarComponent,
    UploadPaperComponent,
    JournalsComponent,
    TasksComponent,
    ReviewerComponent,
    ReviewComponent,
    EditorDecisionComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    NgSelectModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
