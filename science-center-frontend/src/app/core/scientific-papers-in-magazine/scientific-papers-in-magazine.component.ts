import { Component, OnInit } from '@angular/core';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-scientific-papers-in-magazine',
  templateUrl: './scientific-papers-in-magazine.component.html',
  styleUrls: ['./scientific-papers-in-magazine.component.css']
})
export class ScientificPapersInMagazineComponent implements OnInit {

  private relativeUrlForPdfContent = '/scientific-papers/pdf-by-id';
  private relativeUrlForScientificPapersForMagazine = '/scientific-papers/for-magazine';
  private magazineId: number;

  scientificPapers: ScientificPaper[];


  constructor(private genericService: GenericService, private toastr: ToastrService, private route: ActivatedRoute) {
    this.scientificPapers = [];
  }

  ngOnInit() {
    if (this.route.snapshot.params.magazineId) {
      this.magazineId = this.route.snapshot.params.magazineId;
      this.relativeUrlForScientificPapersForMagazine += '?magazineId=' + this.magazineId;
    }

    this.getScientificPapers();
  }

  getScientificPapers() {
    this.genericService.get<ScientificPaper[]>(this.relativeUrlForScientificPapersForMagazine)
    .subscribe(
      (scientificPapers: ScientificPaper[]) => {
        this.scientificPapers = scientificPapers;

        this.toastr.success('Scientific papers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers!');
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
