import { Component, OnInit, NgZone } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { Magazine } from 'src/app/shared/model/magazine';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';
import { RedirectUrlDto } from 'src/app/shared/model/redirect-url-dto';
import { AuthenticationService } from '../services/authentication/authentication.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-magazines-page',
  templateUrl: './magazines-page.component.html',
  styleUrls: ['./magazines-page.component.css']
})
export class MagazinesPageComponent implements OnInit {
  reader: boolean;

  private processInstanceId: string;

  private magazines: Magazine[];

  private currentMagazineId: number;

  private planId: number;

  private relativeUrlForPayment = '/payment/pay';
  private relativeUrlForActivatedMagazinesWithPaidStatus = '/magazines/activated-with-paid-status';
  private relativeUrlForActivatedMagazinesByEditor = '/magazines/activated-by-editor';
  private relativeUrlForStartProcessForAddingPaper = '/scientific-papers/start-process';
  private relativeUrlForSelectMagazine = '/scientific-papers/select-magazine';

  constructor(private genericService: GenericService, private toastr: ToastrService, private authenticationService: AuthenticationService,
              private forwardingMessageService: ForwardingMessageService, private router: Router, private ngZone: NgZone) {
        this.reader = false;
        this.magazines = [];
        this.planId = -1;
   }

  ngOnInit() {
    this.reader = this.isReader();
    this.getActivatedMagazines();

    this.forwardingMessageService.activateMagazineEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.toastr.success('Activating of magazine is done!');
          this.getActivatedMagazines();
        }
      }
    );

    this.forwardingMessageService.magazinesPageCAHAMFEvent.subscribe(
      (hasError: boolean) => {
        if (hasError) {
          this.toastr.info('Pay the membership fee!');
          this.pay(this.currentMagazineId);
        } else {
          this.router.navigate(['/add-scientific-paper']);
        }
      }
    );
  }

  getActivatedMagazines() {
    let relativeUrl;
    if (this.reader) {
      relativeUrl = this.relativeUrlForActivatedMagazinesWithPaidStatus;
    } else {
      relativeUrl = this.relativeUrlForActivatedMagazinesByEditor;
    }
    this.genericService.get<Magazine[]>(relativeUrl).subscribe(
      (magazines: Magazine[]) => {
        this.magazines = magazines;
        this.toastr.success('Magazines loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading magazines!');
      }
    );
  }

  pay(magazineId) {
    if (this.planId === -1) {
      this.toastr.error('You don\'t select plan!');
      return;
    }

    this.genericService.get<RedirectUrlDto>(this.relativeUrlForPayment + '?productId=' + magazineId
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

  startProcessForAddingScientificPaper(magazineId) {
    this.currentMagazineId = magazineId; // privremeno cuvamo
    this.genericService.get<any>(this.relativeUrlForStartProcessForAddingPaper + '?magazineId=' + magazineId)
      .subscribe(
        (res: any) => {
          this.processInstanceId = res.processInstanceId;
          localStorage.setItem('processInstanceId', this.processInstanceId);
          this.toastr.success('startProcessForAddingScientificPaper (success)');

          this.genericService.get<any>(this.relativeUrlForSelectMagazine + '?processInstanceId=' + this.processInstanceId
                                        + '&magazineId=' + magazineId)
            .subscribe(
              () => {
                this.toastr.success('The form was successfully submitted!');
              },
              (err) => alert(JSON.stringify(err))
            );
        },
        (err) => {
          this.toastr.error('startProcessForAddingScientificPaper (fail)');
        }
      );
  }

  isReader() {
    const roles: any = this.authenticationService.getCurrentUser().roles;
    return roles.includes('READER');
  }

}
