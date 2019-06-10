import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {WelcomeComponent} from './components/welcome/welcome.component';
import {HomePageComponent} from './components/home-page/home-page.component';
import {UploadPaperComponent} from './components/upload-paper/upload-paper.component';
import {JournalsComponent} from './components/journals/journals.component';
import {TasksComponent} from './components/tasks/tasks.component';
import {ReviewerComponent} from './components/reviewer/reviewer.component';

const routes: Routes = [
  {path: '', redirectTo: '/welcome', pathMatch: 'full'},
  {path: 'welcome', component: WelcomeComponent},
  {path: 'homepage', component: HomePageComponent},
  {path: 'journals', component: JournalsComponent},
  {path: 'journals/:id/upload-paper', component: UploadPaperComponent},
  {path: 'tasks', component: TasksComponent},
  {path: 'reviewers', component: ReviewerComponent}
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
