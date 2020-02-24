import { Component, OnInit, NgZone } from '@angular/core';
import { ScientificPaper } from 'src/app/shared/model/scientific-paper';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute } from '@angular/router';
import { RedirectUrlDto } from 'src/app/shared/model/redirect-url-dto';

@Component({
  selector: 'app-scientific-papers-in-magazine',
  templateUrl: './scientific-papers-in-magazine.component.html',
  styleUrls: ['./scientific-papers-in-magazine.component.css']
})
export class ScientificPapersInMagazineComponent implements OnInit {

  private relativeUrlForPayment = '/payment/pay';
  private relativeUrlForPdfContent = '/scientific-papers/pdf-by-id';
  private relativeUrlForScientificPapersForMagazine = '/scientific-papers/for-magazine';
  private magazineId: number;

  scientificPapers: ScientificPaper[];
  private planId: number;

  constructor(private genericService: GenericService, private toastr: ToastrService, private route: ActivatedRoute,
              private ngZone: NgZone) {
    this.scientificPapers = [];
    this.planId = -1;
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

  pay(scientificPaperId) {
    if (this.planId === -1) {
      this.toastr.error('You don\'t select plan!');
      return;
    }

    this.genericService.get<RedirectUrlDto>(this.relativeUrlForPayment + '?productId=' + scientificPaperId
                                                     + '&magazine=true&planId=' + this.planId).subscribe(
      (redirectUrlDto: RedirectUrlDto) => {
        this.planId = -1;
        this.ngZone.runOutsideAngular(() => {
          window.location.href = redirectUrlDto.redirectUrl;
        });

        this.toastr.success('Redirection to KP!');
      },
      (err) => {
        this.toastr.error('Problem with payment!');
        alert(JSON.stringify(err));
      }
    );
  }
}
