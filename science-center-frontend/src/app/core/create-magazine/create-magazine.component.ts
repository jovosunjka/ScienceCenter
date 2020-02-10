import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { ToastrService } from 'ngx-toastr';
import { Router, ActivatedRoute } from '@angular/router';
import { ForwardingMessageService } from '../services/forwarding-message/forwarding-message.service';
import { Dto } from 'src/app/shared/model/dto';

@Component({
  selector: 'app-create-magazine',
  templateUrl: './create-magazine.component.html',
  styleUrls: ['./create-magazine.component.css']
})
export class CreateMagazineComponent implements OnInit {
  private relativeUrlForFormFields = '/magazines/create-magazine-form-fields';
  private relativeUrlForUserTaskSubmit = '/magazines/user-task-submit';
  private relativeUrlForScientificAreas = '/scientific-areas/all';
  private taskId: string;
  private formFields: any[];
  private processInstanceId: string;
  private scientificAreas: Dto[];
  private selectedScientificAreasStr: string;

  constructor(private genericService: GenericService, private toastr: ToastrService, private router: Router,
              private forwardingMessageService: ForwardingMessageService, private route: ActivatedRoute) {
    this.selectedScientificAreasStr = '';
    this.scientificAreas = [];
   }

  ngOnInit() {
    if (this.route.snapshot.params.processInstanceId) {
      this.processInstanceId = this.route.snapshot.params.processInstanceId;
      this.relativeUrlForFormFields += '/' + this.processInstanceId;
    }

    this.getAllScientificAreas();
    this.getCreateMagazineFormFields();

    this.forwardingMessageService.saveNewMagazineEvent.subscribe(
      (hasError: boolean) => {
        if (!hasError) {
          this.router.navigate(['/add-editors-and-reviewers']);
        }
      }
    );
  }

  createMagazineSubmit() {
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
    this.genericService.post(this.relativeUrlForUserTaskSubmit.concat('?taskId=').concat(this.taskId), idValueDTOs).subscribe(
        () => this.toastr.success('The form was successfully submitted!'),
        err => alert(JSON.stringify(err))
    );
  }

  isString(x: any): x is string {
    return typeof x === 'string';
  }

  getCreateMagazineFormFields() {
    this.genericService.get<any>(this.relativeUrlForFormFields)
          .subscribe(
            (response: any) => {
                this.taskId = response.taskId;
                this.formFields = response.formFields;
                const oldProcessInstanceId = this.processInstanceId;
                this.processInstanceId = response.processInstanceId;

                localStorage.setItem('processInstanceId', this.processInstanceId);

                this.formFields.forEach(field => {
                    if ( field.type.name === 'enum') {
                      field.type.values = Object.keys(field.type.values);
                    }

                    if (!oldProcessInstanceId) {
                      if (field.type.name === 'boolean') {
                        field.value = false;
                      } else {
                        field.value = '';
                      }
                    } else {
                      field.value = field.value.value;
                      if (field.id === 'scientificAreas') {
                        field.value = field.value.split(',').map(sa => +sa);
                        this.refreshSelectedScientificAreas();
                      }
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
    this.refreshSelectedScientificAreas();
  }

  refreshSelectedScientificAreas() {
    const scientificAreaIds = this.formFields.filter(field => field.id === 'scientificAreas')[0].value;
    const selectedScientificAreas = scientificAreaIds.map(id => this.scientificAreas.filter(sa => sa.id === id)[0].name);
    this.selectedScientificAreasStr = selectedScientificAreas.join(', ');
  }

}
