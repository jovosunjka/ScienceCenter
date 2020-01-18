import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthenticationService } from './services/authentication/authentication.service';
import { ToastrModule } from 'ngx-toastr';
import { GenericService } from './services/generic/generic.service';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { TokenInterceptorService } from './services/token-interceptor/token-interceptor.service';
import { JwtUtilsService } from './services/jwt-utils/jwt-utils.service';
import { AuthModule } from '../auth/auth.module';
import { SharedModule } from '../shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HomePageComponent } from './home-page/home-page.component';
import { AuthorPageComponent } from './author-page/author-page.component';
import { TransactionsPageComponent } from './transactions-page/transactions-page.component';
import { ConfirmRegistrationComponent } from './confirm-registration/confirm-registration.component';
import { AppRoutingModule } from '../routes/app-routing.module';
import { AdministratorPageComponent } from './administrator-page/administrator-page.component';
import { RequestsForReviewerComponent } from './requests-for-reviewer/requests-for-reviewer.component';
import { UsersComponent } from './users/users.component';
import { RolesToStringPipe } from './users/roles-to-string-pipe/roles-to-string.pipe';
import { CreateMagazineComponent } from './create-magazine/create-magazine.component';
import { MagazinesPageComponent } from './magazines-page/magazines-page.component';
import { EditorPageComponent } from './editor-page/editor-page.component';
import { AddEditorsAndReviewersComponent } from './add-editors-and-reviewers/add-editors-and-reviewers.component';
import { AddEditorsComponent } from './add-editors/add-editors.component';
import { AddReviewersComponent } from './add-reviewers/add-reviewers.component';
import { AddPaymentTypesForMagazineComponent } from './add-payment-types-for-magazine/add-payment-types-for-magazine.component';
import { CheckDataForPendingMagazinesComponent } from './check-data-for-pending-magazines/check-data-for-pending-magazines.component';
import { MagazinesWithInvalidDataComponent } from './magazines-with-invalid-data/magazines-with-invalid-data.component';


@NgModule({
  declarations: [
    HomePageComponent,
    AuthorPageComponent,
    TransactionsPageComponent,
    ConfirmRegistrationComponent,
    AdministratorPageComponent,
    RequestsForReviewerComponent,
    UsersComponent,
    RolesToStringPipe,
    CreateMagazineComponent,
    MagazinesPageComponent,
    EditorPageComponent,
    AddEditorsAndReviewersComponent,
    AddEditorsComponent,
    AddReviewersComponent,
    AddPaymentTypesForMagazineComponent,
    CheckDataForPendingMagazinesComponent,
    MagazinesWithInvalidDataComponent
  ],
  imports: [
    CommonModule,
    ToastrModule.forRoot({preventDuplicates: true}), // ToastrModule added,
    AuthModule,
    HttpClientModule,
    SharedModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [
    AuthenticationService,
    GenericService,
    JwtUtilsService,
    { provide: HTTP_INTERCEPTORS, useClass: TokenInterceptorService, multi: true },
    { provide: 'BASE_API_URL', useValue: 'https://localhost:8081/science_center_1' },  // environment.apiUrl
  ]
})
export class CoreModule { }
