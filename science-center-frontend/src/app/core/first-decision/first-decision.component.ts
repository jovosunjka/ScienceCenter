import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ScientificPaperWithReviewings } from 'src/app/shared/model/scientific-paper-with-reviewings';

@Component({
  selector: 'app-first-decision',
  templateUrl: './first-decision.component.html',
  styleUrls: ['./first-decision.component.css']
})
export class FirstDecisionComponent implements OnInit {

  scientificPapers: ScientificPaperWithReviewings[];
  private relativeUrlForScientificPapersForFirstDecision = '/scientific-papers/for-first-decision';
  private relativeUrlFirstDecision = '/scientific-papers/first-decision';

  constructor(private genericService: GenericService, private toastr: ToastrService) { 
    this.scientificPapers = [];
  }

  ngOnInit() {
    this.getScientificPapersForFirstDecision()
  }

  getScientificPapersForFirstDecision() {
    this.genericService.get<ScientificPaperWithReviewings[]>(this.relativeUrlForScientificPapersForFirstDecision).subscribe(
      (scientificPapers: ScientificPaperWithReviewings[]) => {
        this.scientificPapers = scientificPapers;
        this.toastr.success('Scientific papers for first decision loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers for first decision!');
      }
    );
  }

  firstDecisionForPaper(taskId: string, statusAfterReviewing: string) {
    this.genericService.put<any>(this.relativeUrlFirstDecision
          .concat('?taskId=' + taskId),
           {statusAfterReviewing} )
      .subscribe(
        () => {
            this.getScientificPapersForFirstDecision();
            this.toastr.success('First decision for scientific paper is done!');
        },
        err => {
            this.toastr.error('Problem with first decision for scientific paper!');
        }
      );
  }

}
