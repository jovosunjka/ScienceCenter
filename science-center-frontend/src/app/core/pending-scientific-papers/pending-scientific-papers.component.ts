import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';

@Component({
  selector: 'app-pending-scientific-papers',
  templateUrl: './pending-scientific-papers.component.html',
  styleUrls: ['./pending-scientific-papers.component.css']
})
export class PendingScientificPapersComponent implements OnInit {

  private relativeUrlForPdfContent = '/scientific-papers/pdf-by-id';
  private relativeUrlForPendingScientificPapers = '/scientific-papers/pending';
  private magazineId: number;

  scientificPapers: ScientificPaper[];


  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
  }

  ngOnInit() {
    this.getScientificPapers();
  }

  showUsePageButton() {
    return window.location.href.endsWith('/pending-scientific-papers');
  }

  getScientificPapers() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForPendingScientificPapers)
    .subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;

        this.toastr.success('Pending scientific papers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading pending scientific papers!');
      }
    );
  }

  getPdfContentFromServer(scientificPaperId: number, title) {
    this.genericService.getPdfContentFromServer(this.relativeUrlForPdfContent.concat('?scientificPaperId=' + scientificPaperId))
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
