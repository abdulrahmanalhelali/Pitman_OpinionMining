import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountDefinitionsComponent } from './account-definitions/account-definitions.component';
import { LoginComponent } from './pages/login/login.component';
import { MainpageComponent } from './pages/mainpage/mainpage.component';
import { ReportComponent } from './pages/report/report.component';
import { SignupComponent } from './pages/signup/signup.component';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: '', component: MainpageComponent },
  { path: 'signup', component: SignupComponent },
  { path: 'accounts', component: AccountDefinitionsComponent },
  { path: 'report', component: ReportComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
