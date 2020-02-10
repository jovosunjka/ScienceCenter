import { Component, OnInit } from '@angular/core';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-first-repair-scientific-papers',
  templateUrl: './first-repair-scientific-papers.component.html',
  styleUrls: ['./first-repair-scientific-papers.component.css']
})
export class FirstRepairScientificPapersComponent implements OnInit {

  scientificPapers: ScientificPaper[];
  repairedScientificPaperFile: any;

  private relativeUrlForFirstRepair = '/scientific-papers/first-repair';
  private relativeUrlForScientificPapersForFirstRepair = '/scientific-papers/for-first-repair';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
   }

  ngOnInit() {
    this.getScientificPapersForRepairing();
  }

  getScientificPapersForRepairing() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForScientificPapersForFirstRepair).subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;
        this.toastr.success('First repair for Scientific papers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers first repair!');
      }
    );
  }

  onChange(event) {
    this.repairedScientificPaperFile = event.target.files.item(0);
  }

  repairScientificPaper(taskId: string) {
    this.genericService.sendPaperFile(this.relativeUrlForFirstRepair.concat('?taskId=', taskId),
          this.repairedScientificPaperFile)
      .subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
      );
  }
}
