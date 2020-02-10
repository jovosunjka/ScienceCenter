import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-review-scientific-papers',
  templateUrl: './review-scientific-papers.component.html',
  styleUrls: ['./review-scientific-papers.component.css']
})
export class ReviewScientificPapersComponent implements OnInit {
  private relativeUrlForPdfContent = '/scientific-papers/pdf';
  private relativeUrlForScientificPapersForReviewing = '/scientific-papers/for-reviewing';
  private relativeUrlForReviewScientificPaper = '/scientific-papers/review';
  private mainProcessInstanceId: string;

  scientificPapers: ScientificPaper[];


  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
  }

  ngOnInit() {
    this.mainProcessInstanceId = localStorage.getItem('processInstanceId');
    this.getScientificPapersForReviewing();
  }

  getScientificPapersForReviewing() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForScientificPapersForReviewing).subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;
        this.scientificPapers.forEach(sp => {
          sp.commentForAuthor = '';
          sp.commentForEditor = '';
        });

        this.toastr.success('Scientific papers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers!');
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

  reviewPaper(taskId: string, statusAfterReviewing: string, commentForAuthor: string, commentForEditor: string) {
    this.genericService.put<any>(this.relativeUrlForReviewScientificPaper
          .concat('?taskId=' + taskId).concat('&mainProcessInstanceId=' + this.mainProcessInstanceId),
           {statusAfterReviewing, commentForAuthor, commentForEditor} )
      .subscribe(
        () => {
            this.getScientificPapersForReviewing();
            this.toastr.success('Reviewing scientific paper is done!');
        },
        err => {
            this.toastr.error('Problem with Reviewing scientific paper!');
        }
      );
  }

}
