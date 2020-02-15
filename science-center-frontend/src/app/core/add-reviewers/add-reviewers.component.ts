import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { EditorOrReviewer } from 'src/app/shared/model/editor-or-reviewer';

@Component({
  selector: 'app-add-reviewers',
  templateUrl: './add-reviewers.component.html',
  styleUrls: ['./add-reviewers.component.css']
})
export class AddReviewersComponent implements OnInit {
  @Input() taskId: string;
  @Output() refreshEvent: EventEmitter<any> = new EventEmitter<any>();

  private processInstanceId: string;
  reviewers: EditorOrReviewer[];
  selectedReviewers: number[];
  selectedReviewersStr: string;
  private relativeUrlForReviewers = '/users/reviewers-for-magazine-in-current-process';
  private relativeUrlForReviewersForScientificPapers = '/scientific-papers/reviewers-for-scientific-paper';
  private relativeUrlForReviewersForSelectReviewera = '/scientific-papers/select-reviewers';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
      this.reviewers = [];
      this.selectedReviewers = [];
      this.selectedReviewersStr = '';
  }

  ngOnInit() {
    this.processInstanceId = localStorage.getItem('processInstanceId');
    this.getReviewersForMagazine();
  }

  getReviewersForMagazine() {
    let relativeUrl;
    if (this.taskId) {
      relativeUrl = this.relativeUrlForReviewersForScientificPapers.concat('?taskId=').concat(this.taskId);
    } else {
      relativeUrl = this.relativeUrlForReviewers.concat('?processInstanceId=').concat(this.processInstanceId);
    }

    this.genericService.get<EditorOrReviewer[]>(relativeUrl)
      .subscribe(
        (reviewers: EditorOrReviewer[]) => {
          this.reviewers = reviewers;
          this.toastr.success('Reviewers loaded!');
        },
        (err) => {
          this.toastr.error('Problem with loading reviewers!');
        }
      );
  }

  onChangeReviewers($event) {
    const selectedReviewerUsernames = this.selectedReviewers.map(id => this.reviewers.filter(r => r.id === id)[0].username);
    this.selectedReviewersStr = selectedReviewerUsernames.join(', ');
  }

  select() {
    if (this.taskId) {
      if (this.selectedReviewers.length === 1) {
        const mainEditorAsReviewerId = this.selectedReviewers[0];
        if (this.reviewers.filter(r => r.id === mainEditorAsReviewerId && r.mainEditor).length === 0) {
          this.toastr.error('Select at least two reviewers!');
          return;
        }
      } else if (this.selectedReviewers.length === 0) {
        this.toastr.error('Select at least two reviewers!');
        return;
      }

      this.genericService.put<any>(this.relativeUrlForReviewersForSelectReviewera.concat('?taskId=').concat(this.taskId),
                           {reviewers: this.selectedReviewers})
      .subscribe(
        () => {
          this.toastr.success('The form was successfully submitted!');
          this.refreshEvent.emit();
        },
        err => alert(JSON.stringify(err))
      );
    }
  }

}
