import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ScientificPaperWithReviewings } from 'src/app/shared/model/scientific-paper-with-reviewings';

@Component({
  selector: 'app-second-repair-scientific-papers',
  templateUrl: './second-repair-scientific-papers.component.html',
  styleUrls: ['./second-repair-scientific-papers.component.css']
})
export class SecondRepairScientificPapersComponent implements OnInit {
  scientificPapers: ScientificPaperWithReviewings[];
  repairedScientificPaperFile: any;

  private relativeUrlForSecondRepair = '/scientific-papers/second-repair';
  private relativeUrlForScientificPapersForSecondRepair = '/scientific-papers/for-second-repair';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
   }

  ngOnInit() {
    this.getScientificPapersForRepairing();
  }

  getScientificPapersForRepairing() {
    this.genericService.get<ScientificPaperWithReviewings[]>(this.relativeUrlForScientificPapersForSecondRepair).subscribe(
      (scientificPapers: ScientificPaperWithReviewings[]) => {
        this.scientificPapers = scientificPapers;
        this.scientificPapers.forEach(
          sp => sp.answers = ''
        );
        this.toastr.success('Second repair for Scientific papers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers second repair!');
      }
    );
  }

  onChange(event) {
    this.repairedScientificPaperFile = event.target.files.item(0);
  }

  repairScientificPaper(taskId: string, answers: string) {
    this.genericService.sendPaper(this.relativeUrlForSecondRepair.concat('?taskId=', taskId),
          this.repairedScientificPaperFile, {answers})
      .subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
      );
  }
}
