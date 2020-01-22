import {Component, NgZone, OnInit} from '@angular/core';
import {GenericService} from '../services/generic/generic.service';
import {ToastrService} from 'ngx-toastr';
import {Dto} from '../../shared/model/dto';
import { RedirectUrlDto } from 'src/app/shared/model/redirect-url-dto';

@Component({
  selector: 'app-author-page',
  templateUrl: './author-page.component.html',
  styleUrls: ['./author-page.component.css']
})
export class AuthorPageComponent implements OnInit {
  magazineId: number;
  magazines: Dto[];
  private relativeUrlForPayment = '/payment/pay';
  private relativeUrlForAllMagazines = '/magazines/all-activated';

  constructor(private genericService: GenericService, private ngZone: NgZone, private toastr: ToastrService) {
    this.magazines = [];
  }

  ngOnInit() {
    this.getAllMagazines();
  }

  getAllMagazines() {
    this.genericService.get<Dto[]>(this.relativeUrlForAllMagazines).subscribe(
      (magazines: Dto[]) => {
        this.magazines = magazines;
        if (this.magazines && this.magazines.length > 0) {
          this.magazineId = this.magazines[0].id;
        }
        this.toastr.success('Magazines loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading magazines!');
      }
    );
  }

  pay() {
    this.genericService.get<RedirectUrlDto>(this.relativeUrlForPayment + '/' + this.magazineId).subscribe(
      (redirectUrlDto: RedirectUrlDto) => {
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
