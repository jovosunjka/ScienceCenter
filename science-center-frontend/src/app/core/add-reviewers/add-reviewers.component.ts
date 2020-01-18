import { Component, OnInit, Input } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { EditorOrReviewer } from 'src/app/shared/model/editor-or-reviewer';

@Component({
  selector: 'app-add-reviewers',
  templateUrl: './add-reviewers.component.html',
  styleUrls: ['./add-reviewers.component.css']
})
export class AddReviewersComponent implements OnInit {
  private processInstanceId: string;
  reviewers: EditorOrReviewer[];
  selectedReviewers: number[];
  selectedReviewersStr: string;
  private relativeUrlForReviewers = '/users/reviewers-for-magazine-in-current-process';

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
    this.genericService.get<EditorOrReviewer[]>(this.relativeUrlForReviewers.concat('?processInstanceId=').concat(this.processInstanceId))
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

}
