import { Component, OnInit, Input } from '@angular/core';
import { EditorOrReviewer } from 'src/app/shared/model/editor-or-reviewer';
import { ToastrService } from 'ngx-toastr';
import { GenericService } from '../services/generic/generic.service';

@Component({
  selector: 'app-add-editors',
  templateUrl: './add-editors.component.html',
  styleUrls: ['./add-editors.component.css']
})
export class AddEditorsComponent implements OnInit {
  private processInstanceId: string;
  editors: EditorOrReviewer[];
  selectedEditors: number[];
  selectedEditorsStr: string;
  private relativeUrlForEditors = '/users/editors-for-magazine-in-current-process';

  constructor(private genericService: GenericService, private toastr: ToastrService) {
      this.editors = [];
      this.selectedEditors = [];
      this.selectedEditorsStr = '';
   }

  ngOnInit() {
    this.processInstanceId = localStorage.getItem('processInstanceId');
    this.getEditorsForMagazine();
  }

  getEditorsForMagazine() {
    this.genericService.get<EditorOrReviewer[]>(this.relativeUrlForEditors.concat('?processInstanceId=').concat(this.processInstanceId))
      .subscribe(
        (editors: EditorOrReviewer[]) => {
          this.editors = editors;
          this.toastr.success('Editors loaded!');
        },
        (err) => {
          this.toastr.error('Problem with loading editors!');
        }
      );
  }

  onChangeEditors($event) {
    const selectedEditorUsernames = this.selectedEditors.map(id => this.editors.filter(e => e.id === id)[0].username);
    this.selectedEditorsStr = selectedEditorUsernames.join(', ');
  }

}
