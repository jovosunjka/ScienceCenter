import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { ResultData } from 'src/app/shared/model/result-data';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {

  private relativeUrlForSearch = '/search/custom';
  private relativeUrlForPdfContent = '/scientific-papers/pdf-by-id';

  magazineName: string;
  title: string;
  authors: string;
  keywords: string;
  scientificArea: string;

  titeleOperator: string;
  authorsOperator: string;
  keywordsOperator: string;
  scientificAreaOperator: string;

  results: ResultData[];

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.magazineName = '';
    this.title = '';
    this.authors = '';
    this.keywords = '';
    this.scientificArea = '';

    this.titeleOperator = 'AND';
    this.authorsOperator = 'AND';
    this.keywordsOperator = 'AND';
    this.scientificAreaOperator = 'AND';
  }

  ngOnInit() {
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

  search() {
    const queries = [
      {
        operator: 'NO_OPERATOR',
        field: 'magazinename',
        value: this.magazineName
      },
      {
        operator: this.titeleOperator,
        field: 'title',
        value: this.title
      },
      {
        operator: this.authorsOperator,
        field: 'authors',
        value: this.authors
      },
      {
        operator: this.keywordsOperator,
        field: 'keywords',
        value: this.keywords
      },
      {
        operator: this.scientificAreaOperator,
        field: 'scientificarea',
        value: this.scientificArea
      }
    ];

    this.genericService.post(this.relativeUrlForSearch, { queries }).subscribe(
      (results: ResultData[]) => {
        this.results = results;
        this.toastr.success('Searching is done!');
        // alert(JSON.stringify(results));
      },
      err => alert(JSON.stringify(err))
    );
  }
}
