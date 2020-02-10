import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { Dto } from 'src/app/shared/model/dto';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-scientific-paper',
  templateUrl: './add-scientific-paper.component.html',
  styleUrls: ['./add-scientific-paper.component.css']
})
export class AddScientificPaperComponent implements OnInit {
  private relativeUrlForAddScientificPaper = '/scientific-papers/add';
  private relativeUrlForFormFields = '/scientific-papers/add-scientific-paper-form-fields';
  private relativeUrlForScientificAreas = '/scientific-areas/all';

  private scientificAreas: Dto[];

  newCoauthor: any;

  scientificPaperFile: any;
  private formFields: any[];
  private processInstanceId: string;


  constructor(private genericService: GenericService, private toastr: ToastrService, private router: Router) {
    this.scientificAreas = [];
    this.newCoauthor = {
      firstName: '',
      lastName: '',
      email: '',
      address: '',
      city: '',
      country: ''
    };
  }

  ngOnInit() {
    this.processInstanceId = localStorage.getItem('processInstanceId');
    this.relativeUrlForFormFields += '?processInstanceId=' + this.processInstanceId;

    this.getAllScientificAreas();
    this.getCreateMagazineFormFields();
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
        () => {
          this.toastr.success('The form was successfully submitted!');
          this.router.navigate(['/user-page']);
        },
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

}
