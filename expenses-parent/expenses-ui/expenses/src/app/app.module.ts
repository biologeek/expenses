import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatButtonModule, MatFormFieldModule, MatInputModule, MatGridListModule, MatCardModule } from '@angular/material';

import { CookieService } from 'ngx-cookie-service';
import { AppComponent } from './app.component';
import { ConnectionComponent } from './connection/connection.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { OperationEditorComponent } from './operation-editor/operation-editor.component';
import { OperationListComponent } from './operation-list/operation-list.component';
import { AccountChoiceComponent } from './account-choice/account-choice.component';
import { UserService } from './services/user.service';
import { RouterModule, Route } from '@angular/router';
import { HttpError401Interceptor } from './services/http-error401-interceptor.service';
import { HttpClientModule } from '@angular/common/http';
import { MaterialModule } from './material.module';



export const appRoutes: Route[] = [
  {
    path: 'dashboard', component: DashboardComponent
  }, {
    path: '', component: ConnectionComponent
  }, {
    path: 'new', component: OperationEditorComponent
  }, {
    path: 'list', component: OperationListComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    ConnectionComponent,
    DashboardComponent,
    OperationEditorComponent,
    OperationListComponent,
    AccountChoiceComponent
  ],
  imports: [
    BrowserModule, FormsModule,
    RouterModule.forRoot(appRoutes),
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    ReactiveFormsModule
  ],
  providers: [CookieService, UserService, HttpError401Interceptor],
  bootstrap: [AppComponent]
})
export class AppModule { }
