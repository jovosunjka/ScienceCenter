import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-select-reviewers-for-scientific-papers',
  templateUrl: './select-reviewers-for-scientific-papers.component.html',
  styleUrls: ['./select-reviewers-for-scientific-papers.component.css']
})
export class SelectReviewersForScientificPapersComponent implements OnInit {

  scientificPapers: any[];
  repairedScientificPaperFile: any;

  private relativeUrl = '/scientific-papers/for-selecting-reviewers';
 
  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.scientificPapers = [];
   }

  ngOnInit() {
    this.getScientificPapersForSelectingReviewers();
  }

  getScientificPapersForSelectingReviewers() {
    this.genericService.get<any[]>(this.relativeUrl).subscribe(
      (scientificPapers: any[]) => {
        this.scientificPapers = scientificPapers;
        this.toastr.success('Scientific papers for selecting reviewers loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading scientific papers for selecting reviewers!');
      }
    );
  }
}
