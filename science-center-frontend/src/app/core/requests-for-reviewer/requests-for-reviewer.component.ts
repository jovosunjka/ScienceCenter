import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { GenericService } from '../services/generic/generic.service';
import { RequestForReviewerDto } from 'src/app/shared/model/request-for-reviewer-dto';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';

@Component({
  selector: 'app-requests-for-reviewer',
  templateUrl: './requests-for-reviewer.component.html',
  styleUrls: ['./requests-for-reviewer.component.css']
})
export class RequestsForReviewerComponent implements OnInit {

  private relativeUrlForAllUsers = '/users/requests-for-reviewer';
  private relativeUrlForConfirmReviewerStatus = '/users/confirm-reviewer-status';

  requestsForReviewer: RequestForReviewerDto[];


  constructor(private genericService: GenericService, private toastr: ToastrService,
              private forwardingMessageService: ForwardingMessageService) {
    this.requestsForReviewer = [];
  }

  ngOnInit() {
    this.getAllRequestsForReviewer();

    this.forwardingMessageService.confirmReviewerStatusEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.toastr.success('Request for reviewer is done!');
          this.getAllRequestsForReviewer();
        }
      }
    );
  }

  getAllRequestsForReviewer() {
    this.genericService.get<RequestForReviewerDto[]>(this.relativeUrlForAllUsers).subscribe(
      (requestsForReviewer: RequestForReviewerDto[]) => {
        this.requestsForReviewer = requestsForReviewer;
        this.toastr.success('RequestsForReviewer loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading requestsForReviewer!');
      }
    );
  }

  confirm(taskId: string, confirmId: number, confirmed: boolean) {
    this.genericService.put<any>(this.relativeUrlForConfirmReviewerStatus.concat('?taskId=' + taskId), {confirmId, confirmed})
      .subscribe(
        () => {
          if (confirmed) {
            this.toastr.success('Reviewer allowing status - started!');
          } else {
            this.toastr.success('Reviewer declining status - started!');
          }
        },
        err => {
          if (confirmed) {
            this.toastr.error('Problem with allowing reviewer status!');
          } else {
            this.toastr.error('Problem with declining reviewer status!');
          }
        }
      );
  }

}
