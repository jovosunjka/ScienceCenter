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
  private relativeUrlForPdfContent = '/scientific-papers/pdf';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
  }

  ngOnInit() {
    this.getScientificPapersForFirstDecision();
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

  firstDecisionForPaper(taskId: string, editorOfScientificAreaDecision: string) {
    this.genericService.put<any>(this.relativeUrlFirstDecision
          .concat('?taskId=' + taskId),
           {editorOfScientificAreaDecision} )
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
