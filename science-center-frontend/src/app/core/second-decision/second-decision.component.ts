import { Component, OnInit } from '@angular/core';
import { ScientificPaperWithReviewings } from 'src/app/shared/model/scientific-paper-with-reviewings';
import { ToastrService } from 'ngx-toastr';
import { GenericService } from '../services/generic/generic.service';

@Component({
  selector: 'app-second-decision',
  templateUrl: './second-decision.component.html',
  styleUrls: ['./second-decision.component.css']
})
export class SecondDecisionComponent implements OnInit {

  scientificPapers: ScientificPaperWithReviewings[];
  private relativeUrlForScientificPapersForFirstDecision = '/scientific-papers/for-second-decision';
  private relativeUrlFirstDecision = '/scientific-papers/second-decision';
  private relativeUrlForPdfContent = '/scientific-papers/pdf';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
  }

  ngOnInit() {
    this.getScientificPapersForSecondDecision();
  }

  getScientificPapersForSecondDecision() {
    this.genericService.get<ScientificPaperWithReviewings[]>(this.relativeUrlForScientificPapersForFirstDecision).subscribe(
      (scientificPapers: ScientificPaperWithReviewings[]) => {
        this.scientificPapers = scientificPapers;
        this.scientificPapers.forEach(sp => sp.commentForAuthor = '');
        this.toastr.success('Scientific papers for first decision loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers for second decision!');
      }
    );
  }

  secondDecisionForPaper(taskId: string, editorDecision: string, editorCommentForAuthor: string) {
    this.genericService.put<any>(this.relativeUrlFirstDecision
          .concat('?taskId=' + taskId),
           {editorDecision, editorCommentForAuthor} )
      .subscribe(
        () => {
            this.getScientificPapersForSecondDecision();
            this.toastr.success('Second decision for scientific paper is done!');
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
