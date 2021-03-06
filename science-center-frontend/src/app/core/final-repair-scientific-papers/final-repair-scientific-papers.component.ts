import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';

@Component({
  selector: 'app-final-repair-scientific-papers',
  templateUrl: './final-repair-scientific-papers.component.html',
  styleUrls: ['./final-repair-scientific-papers.component.css']
})
export class FinalRepairScientificPapersComponent implements OnInit {

  scientificPapers: ScientificPaper[];
  repairedScientificPaperFile: any;

  private relativeUrlForFinalRepair = '/scientific-papers/final-repair';
  private relativeUrlForScientificPapersForFinalRepair = '/scientific-papers/for-final-repair';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
   }

  ngOnInit() {
    this.getScientificPapersForRepairing();
  }

  getScientificPapersForRepairing() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForScientificPapersForFinalRepair).subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;
        this.scientificPapers.forEach(sp => sp.answers = '');
        this.toastr.success('Final repair for Scientific papers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers final repair!');
      }
    );
  }

  onChange(event) {
    this.repairedScientificPaperFile = event.target.files.item(0);
  }

  repairScientificPaper(taskId: string, answers: string) {
    this.genericService.sendPaper(this.relativeUrlForFinalRepair.concat('?taskId=', taskId),
          this.repairedScientificPaperFile, {answers})
      .subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
      );
  }
}
