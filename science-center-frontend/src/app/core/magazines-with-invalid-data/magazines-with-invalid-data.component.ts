import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { Router } from '@angular/router';


@Component({
  selector: 'app-magazines-with-invalid-data',
  templateUrl: './magazines-with-invalid-data.component.html',
  styleUrls: ['./magazines-with-invalid-data.component.css']
})
export class MagazinesWithInvalidDataComponent implements OnInit {

  private magazines: any[];

  private relativeUrlForAllActivatedMagazines = '/magazines/magazines-with-invalid-data';

  constructor(private genericService: GenericService,  private router: Router, private toastr: ToastrService) {
    this.magazines = [];
   }

  ngOnInit() {
    this.getMagazinesWithInvalidData();
  }

  getMagazinesWithInvalidData() {
    this.genericService.get<any[]>(this.relativeUrlForAllActivatedMagazines).subscribe(
      (magazines: any[]) => {
        this.magazines = magazines;
        this.toastr.success('Magazines with invalid data loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading magazines with invalid data!');
      }
    );
  }

  editMagazine(processInstanceId: string) {
    this.router.navigate(['/create-magazine/'.concat(processInstanceId)]);
  }
}
