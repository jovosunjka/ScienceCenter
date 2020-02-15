import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { HomePageComponent } from '../core/home-page/home-page.component';
import {AuthorPageComponent} from '../core/author-page/author-page.component';
import { TransactionsPageComponent } from '../core/transactions-page/transactions-page.component';
import { LoginPageComponent } from '../auth/login-page/login-page.component';
import { RegisterPageComponent } from '../auth/register-page/register-page.component';
import { ConfirmRegistrationComponent } from '../core/confirm-registration/confirm-registration.component';
import { AdministratorPageComponent } from '../core/administrator-page/administrator-page.component';
import { CanActivateUserGuard } from './guard/can-activate-user.guard';
import { CreateMagazineComponent } from '../core/create-magazine/create-magazine.component';
import { MagazinesPageComponent } from '../core/magazines-page/magazines-page.component';
import { EditorPageComponent } from '../core/editor-page/editor-page.component';
import { AddEditorsAndReviewersComponent } from '../core/add-editors-and-reviewers/add-editors-and-reviewers.component';
import { AddPaymentTypesForMagazineComponent } from '../core/add-payment-types-for-magazine/add-payment-types-for-magazine.component';
import { AddScientificPaperComponent } from '../core/add-scientific-paper/add-scientific-paper.component';
import { UserPageComponent } from '../core/user-page/user-page.component';
import { ScientificPapersInMagazineComponent } from '../core/scientific-papers-in-magazine/scientific-papers-in-magazine.component';
import { PendingScientificPapersComponent } from '../core/pending-scientific-papers/pending-scientific-papers.component';


const routes: Routes = [
  { path: 'home-page', component: HomePageComponent },
  { path: 'login', component: LoginPageComponent },
  { path: 'register', component: RegisterPageComponent },
  {
      path: 'administrator-page',
      component: AdministratorPageComponent,
      data: {
        expectedRoles: ['ADMINISTRATOR']
      },
      canActivate: [CanActivateUserGuard]
  },
  { path: 'confirm-registration', component: ConfirmRegistrationComponent },
  {
      path: '', // localhost:4200 redirect to localhost:4200/home-page
      redirectTo: '/home-page',
      pathMatch: 'full'
  },
  {
      path: 'author-page',
      component: AuthorPageComponent,
      data: {
        expectedRoles: ['AUTHOR']
      },
      canActivate: [CanActivateUserGuard]
  },
  {
      path: 'transactions-page',
      component: TransactionsPageComponent,
      data: {
        expectedRoles: ['USER']
      },
      canActivate: [CanActivateUserGuard]
  },
  {
      path: 'editor-page',
      component: EditorPageComponent,
      data: {
        expectedRoles: ['EDITOR']
      },
      canActivate: [CanActivateUserGuard]
  },
  {
      path: 'create-magazine/:processInstanceId',
      component: CreateMagazineComponent,
      data: {
        expectedRoles: ['EDITOR']
      },
      canActivate: [CanActivateUserGuard]
  },
  {
      path: 'create-magazine',
      component: CreateMagazineComponent,
      data: {
        expectedRoles: ['EDITOR']
      },
      canActivate: [CanActivateUserGuard]
  },
  { path: 'magazines-page', component: MagazinesPageComponent },
  {
      path: 'add-editors-and-reviewers',
      component: AddEditorsAndReviewersComponent,
      data: {
        expectedRoles: ['EDITOR', 'READER']
      },
      canActivate: [CanActivateUserGuard]
  },
  {
      path: 'user-page',
      component: UserPageComponent,
      data: {
        expectedRoles: ['USER', 'READER']
      },
      canActivate: [CanActivateUserGuard]
  },
  {
      path: 'add-payment-types-for-magazine',
      component: AddPaymentTypesForMagazineComponent,
      data: {
        expectedRoles: ['EDITOR']
      },
      canActivate: [CanActivateUserGuard]
  },
  {
    path: 'add-scientific-paper',
    component: AddScientificPaperComponent,
    data: {
      expectedRoles: ['USER']
    },
    canActivate: [CanActivateUserGuard]
  },
  {
    path: 'scientific-papers-in-magazine/:magazineId',
    component: ScientificPapersInMagazineComponent,
    data: {
      expectedRoles: ['USER']
    },
    canActivate: [CanActivateUserGuard]
  },
  {
    path: 'pending-scientific-papers',
    component: PendingScientificPapersComponent,
    data: {
      expectedRoles: ['USER']
    },
    canActivate: [CanActivateUserGuard]
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
