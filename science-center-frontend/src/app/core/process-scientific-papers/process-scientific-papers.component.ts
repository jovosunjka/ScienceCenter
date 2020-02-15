import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-process-scientific-papers',
  templateUrl: './process-scientific-papers.component.html',
  styleUrls: ['./process-scientific-papers.component.css']
})
export class ProcessScientificPapersComponent implements OnInit {

  private relativeUrlForScientificPapersForProcessing = '/scientific-papers/for-processing';
  private relativeUrlForProcessScientificPaper = '/scientific-papers/process';
  private relativeUrlForPdfContent = '/scientific-papers/pdf';

  scientificPapers: ScientificPaper[];


  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
  }

  ngOnInit() {
    this.getScientificPapersForProcessing();
  }

  getScientificPapersForProcessing() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForScientificPapersForProcessing).subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;
        this.scientificPapers.forEach(sp => sp.commentForAuthor = '');

        this.toastr.success('Scientific papers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers!');
      }
    );
  }
  processPaper(taskId: string, status: string, comment: string) {
    this.genericService.put<any>(this.relativeUrlForProcessScientificPaper.concat('?taskId=' + taskId), {status, comment} )
      .subscribe(
        () => {
            this.getScientificPapersForProcessing();
            this.toastr.success('Processing scientific paper is done!');
        },
        err => {
            this.toastr.error('Problem with processing scientific paper!');
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
