import { Component, OnInit } from '@angular/core';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-final-decision',
  templateUrl: './final-decision.component.html',
  styleUrls: ['./final-decision.component.css']
})
export class FinalDecisionComponent implements OnInit {

  scientificPapers: ScientificPaper[];
  private relativeUrlForScientificPapersForFinalDecision = '/scientific-papers/for-final-decision';
  private relativeUrlFinalDecision = '/scientific-papers/final-decision';
  private relativeUrlForPdfContent = '/scientific-papers/pdf';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
  }

  ngOnInit() {
    this.getScientificPapersForFinalDecision();
  }

  getScientificPapersForFinalDecision() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForScientificPapersForFinalDecision).subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;
        this.toastr.success('Scientific papers for final decision loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers for final decision!');
      }
    );
  }

  finalDecisionForPaper(taskId: string, editorDecision: string) {
    this.genericService.put<any>(this.relativeUrlFinalDecision
          .concat('?taskId=' + taskId),
           {editorDecision} )
      .subscribe(
        () => {
            this.getScientificPapersForFinalDecision();
            this.toastr.success('Final decision for scientific paper is done!');
        },
        err => {
            this.toastr.error('Problem with final decision for scientific paper!');
        }
      );
  }

  getPdfContentFromServer(taskId: string, title) {
    this.genericService.getPdfContentFromServer(this.relativeUrlForPdfContent.concat('?taskId=' + taskId))
          .subscribe( (pdf: any) => {
              console.log('pdf response: ', pdf);
              const mediaType = 'application/pdf';
              const blob = new Blob([pdf], {type: mediaType});

              this.download(title, blob);
            },
            err => console.log('Pdf generated err: ', JSON.stringify(err))
          );
  }

  download(fileName, content) {
    const element = document.createElement('a');
    /*element.setAttribute('href', 'data:application/pdf;charset=utf-8,' + encodeURIComponent(content));*/
    element.setAttribute('href', window.URL.createObjectURL(content));
    element.setAttribute('download', fileName + '.pdf');

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
  }

}
