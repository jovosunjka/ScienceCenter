import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { GenericService } from 'src/app/core/services/generic/generic.service';
import { Dto } from 'src/app/shared/model/dto';
import { ToastrService } from 'ngx-toastr';
import { ForwardingMessageService } from 'src/app/core/services/forwarding-message/forwarding-message.service';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {
  private relativeUrlForFormFields = '/users/registration-form-fields';
  private relativeUrlForUserTask = '/users/user-task-submit';
  private relativeUrlForScientificAreas = '/scientific-areas/all';
  private taskId: string;
  private formFields: any[];
  private processInstanceId: string;
  private scientificAreas: Dto[];
  private selectedScientificAreasStr: string;

  constructor(private genericService: GenericService, private toastr: ToastrService, private router: Router,
              private forwardingMessageService: ForwardingMessageService) {
    this.selectedScientificAreasStr = '';
    this.scientificAreas = [];
   }

  ngOnInit() {
    this.getAllScientificAreas();
    this.getRegistrationFormFields();

    this.forwardingMessageService.registerPageEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.router.navigate(['/login']);
        }
      }
    );
  }

  registrationSubmit() {
    const idValueDTOs = [];
    this.formFields.forEach(field => {
        if (field.id === 'scientificAreas') {
          if (field.value) {
            if (!this.isString(field.value)) {
              field.value = field.value.join(',');
            }
          } else {
            field.value = '';
          }
        }
        idValueDTOs.push( {id: field.id, value: field.value} );
      }
    );
    this.genericService.post(this.relativeUrlForUserTask.concat('?taskId=').concat(this.taskId), idValueDTOs).subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
      );
  }

  isString(x: any): x is string {
    return typeof x === 'string';
  }

  getRegistrationFormFields() {
    this.genericService.get<any>(this.relativeUrlForFormFields)
          .subscribe(
            (response: any) => {
                this.taskId = response.taskId;
                this.formFields = response.formFields;
                this.processInstanceId = response.processInstanceId;

                localStorage.setItem('processInstanceId', this.processInstanceId);

                this.formFields.forEach(field => {
                    if ( field.type.name === 'enum') {
                      field.type.values = Object.keys(field.type.values);
                    }

                    if (field.type.name === 'boolean') {
                      field.value = false;
                    } else {
                      field.value = '';
                    }
                  }
                );
            },
            err => alert(JSON.stringify(err))
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

  onChangeScientificAera($event) {
    const scientificAreaIds = this.formFields.filter(field => field.id === 'scientificAreas')[0].value;
    const selectedScientificAreas = scientificAreaIds.map(id => this.scientificAreas.filter(sa => sa.id === id)[0].name);
    this.selectedScientificAreasStr = selectedScientificAreas.join(', ');
  }

}
