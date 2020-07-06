import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { Dto } from 'src/app/shared/model/dto';
import { Router } from '@angular/router';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';

@Component({
  selector: 'app-add-scientific-paper',
  templateUrl: './add-scientific-paper.component.html',
  styleUrls: ['./add-scientific-paper.component.css']
})
export class AddScientificPaperComponent implements OnInit {
  private relativeUrlForAddScientificPaper = '/scientific-papers/add';
  private relativeUrlForFormFields = '/scientific-papers/add-scientific-paper-form-fields';
  private relativeUrlForScientificAreas = '/scientific-areas/all';
  private relativeUrlForCities = '/scientific-papers/cities';

  private scientificAreas: Dto[];

  cities: string[];

  newCoauthor: any;

  scientificPaperFile: any;
  private formFields: any[];
  private processInstanceId: string;

  private plans = [];
  newPlan: any;

  constructor(private genericService: GenericService, private toastr: ToastrService, private router: Router,
              private forwardingMessageService: ForwardingMessageService) {
    this.scientificAreas = [];
    this.cities = [];
    this.newCoauthor = {
      firstName: '',
      lastName: '',
      email: '',
      address: '',
      city: '',
      country: ''
    };
    this.newPlan = {
      intervalUnit: '',
      intervalCount: 1,
      price: 1
    };
  }

  ngOnInit() {
    this.processInstanceId = localStorage.getItem('processInstanceId');
    this.relativeUrlForFormFields += '?processInstanceId=' + this.processInstanceId;

    this.getCities();
    this.getAllScientificAreas();
    this.getCreateMagazineFormFields();

    this.forwardingMessageService.addNewScientificPaperEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.router.navigate(['/pending-scientific-papers']);
        }
      }
    );
  }

  getAllScientificAreas() {
    this.genericService.get<Dto[]>(this.relativeUrlForScientificAreas)
          .subscribe(
            (scientificAreas: Dto[]) => {
                this.scientificAreas = scientificAreas;
                this.toastr.success('Scientific areas loaded!');
            },
            err => alert(JSON.stringify(err))
          );
  }

  getCities() {
    this.genericService.get<string[]>(this.relativeUrlForCities)
          .subscribe(
            (cities: string[]) => {
                this.cities = cities;
                this.toastr.success('Cities loaded!');
            },
            err => alert(JSON.stringify(err))
          );
  }

  getCreateMagazineFormFields() {
    this.genericService.get<any>(this.relativeUrlForFormFields)
          .subscribe(
            (response: any) => {
                // this.taskId = response.taskId;
                this.formFields = response.formFields;
                // const oldProcessInstanceId = this.processInstanceId;
                this.processInstanceId = response.processInstanceId;

                // localStorage.setItem('processInstanceId', this.processInstanceId);

                this.formFields.forEach(field => {
                  if (field.id === 'coauthors') {
                    field.value = [];
                  } else if (field.id === 'plans') {
                    field.value = [];
                  } else {
                    field.value = '';
                  }
                });
            },
            err => alert(JSON.stringify(err))
          );
  }

  addScientificPaper() {
    const idValueDTOs = [];
    this.formFields.forEach(field => {
        let fValue;
        if (field.id === 'coauthors') {
          fValue = JSON.stringify(field.value);
        } else if (field.id === 'plans') {
          fValue = JSON.stringify(field.value);
        } else if (field.id === 'selectedScientificAreaId') {
          fValue = '' + field.value;
        } else {
          fValue = field.value;
        }

        if (field.id !== 'fileName') {
          idValueDTOs.push( {id: field.id, value: fValue} );
        }
      }
    );

    this.genericService.sendPaper(this.relativeUrlForAddScientificPaper.concat('?processInstanceId=', this.processInstanceId),
              this.scientificPaperFile, idValueDTOs)
      .subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
      );
  }

  addCoauthor() {
    this.newCoauthor.firstName = this.newCoauthor.firstName.trim();
    this.newCoauthor.lastName = this.newCoauthor.lastName.trim();
    this.newCoauthor.email = this.newCoauthor.email.trim();
    this.newCoauthor.address = this.newCoauthor.address.trim();
    this.newCoauthor.city = this.newCoauthor.city.trim();
    this.newCoauthor.country = this.newCoauthor.country.trim();

    if (this.newCoauthor.firstName === '' || this.newCoauthor.lastName === '' || this.newCoauthor.email === ''
        || this.newCoauthor.address === '' || this.newCoauthor.city === '' || this.newCoauthor.country === '') {
          this.toastr.error('You have not filled all the fields for new coauthor!');
          return;
    }

    const coauthors: any[] = this.formFields.filter(ff => ff.id === 'coauthors')[0].value;
    coauthors.push( {...this.newCoauthor});

    this.newCoauthor.firstName = '';
    this.newCoauthor.lastName = '';
    this.newCoauthor.email = '';
    this.newCoauthor.address = '';
    this.newCoauthor.city = '';
    this.newCoauthor.country = '';
  }

  removeCoauthor(coauthor: any) {
    const coauthors: any[] = this.formFields.filter(ff => ff.id === 'coauthors')[0].value;
    const index = coauthors.indexOf(coauthor);
    coauthors.splice(index, 1);
  }

  onChange(event) {
    this.scientificPaperFile = event.target.files.item(0);
  }

  addPlan() {
    this.newPlan.intervalUnit = this.newPlan.intervalUnit.trim();
    this.newPlan.intervalCount = this.newPlan.intervalCount;
    this.newPlan.price = this.newPlan.price;

    if (this.newPlan.intervalUnit === '') {
          this.toastr.error('You have not filled all the fields for new plan!');
          return;
    }

    const plan: any[] = this.formFields.filter(ff => ff.id === 'plans')[0].value;
    plan.push( {...this.newPlan});

    this.newPlan.intervalUnit = '';
    this.newPlan.intervalCount = 1;
    this.newPlan.price = 1;
  }

  removePlan(plan: any) {
    const plans: any[] = this.formFields.filter(ff => ff.id === 'plans')[0].value;
    const index = plans.indexOf(plan);
    plans.splice(index, 1);
  }

}
