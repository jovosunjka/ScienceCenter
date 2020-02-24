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

  search() {
    const queries = [
      {
        operator: 'NO_OPERATOR',
        field: 'magazineName',
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
        field: 'scientificArea',
        value: this.scientificArea
      }
    ];

    this.genericService.post(this.relativeUrlForSearch, { queries }).subscribe(
      (results: ResultData[]) => {
        this.results = results;
        this.toastr.success('Searching is done!');
        alert(JSON.stringify(results));
      },
      err => alert(JSON.stringify(err))
    );
  }
}
