import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AddEditorsComponent } from '../add-editors/add-editors.component';
import { AddReviewersComponent } from '../add-reviewers/add-reviewers.component';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-editors-and-reviewers',
  templateUrl: './add-editors-and-reviewers.component.html',
  styleUrls: ['./add-editors-and-reviewers.component.css']
})
export class AddEditorsAndReviewersComponent implements OnInit {

  private processInstanceId: string;
  private relativeUrlForAddEditroaAndReviewers = '/magazines/add-editors-and-reviewers';

  @ViewChild(AddEditorsComponent, {static: false})
  private addEditorsComponent: AddEditorsComponent;

  @ViewChild(AddReviewersComponent, {static: false})
  private addReviewersComponent: AddReviewersComponent;

  constructor(private genericService: GenericService, private toastr: ToastrService,
              private forwardingMessageService: ForwardingMessageService, private router: Router) { }

  ngOnInit() {
    this.processInstanceId = localStorage.getItem('processInstanceId');

    this.forwardingMessageService.addEditorsAndReviewersEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.router.navigate(['/add-payment-types-for-magazine']);
        }
      }
    );
  }

  next() {
    const editors =  this.addEditorsComponent.selectedEditors.join(',');
    const reviewers =  this.addReviewersComponent.selectedReviewers.join(',');

    this.genericService.put<any>(this.relativeUrlForAddEditroaAndReviewers.concat('?processInstanceId=').concat(this.processInstanceId),
                           {editors, reviewers})
      .subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
      );
  }

}
