import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';

@Component({
  selector: 'app-repair-scientific-papers',
  templateUrl: './repair-scientific-papers.component.html',
  styleUrls: ['./repair-scientific-papers.component.css']
})
export class RepairScientificPapersComponent implements OnInit {

  scientificPapers: ScientificPaper[];
  repairedScientificPaperFile: any;

  private relativeUrlForRepairingScientificPaper = '/scientific-papers/repair';
  private relativeUrlForScientificPapersForRepairing = '/scientific-papers/for-repairing';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
   }

  ngOnInit() {
    this.getScientificPapersForRepairing();
  }

  getScientificPapersForRepairing() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForScientificPapersForRepairing).subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;
        this.toastr.success('Scientific papers for repairing loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers for repairing!');
      }
    );
  }

  onChange(event) {
    this.repairedScientificPaperFile = event.target.files.item(0);
  }

  repairScientificPaper(taskId: string) {
    this.genericService.sendPaperFile(this.relativeUrlForRepairingScientificPaper.concat('?taskId=', taskId),
          this.repairedScientificPaperFile)
      .subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
      );
  }

}
