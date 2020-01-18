import { Component, OnInit } from '@angular/core';
import { GenericService } from '../services/generic/generic.service';
import { UserDto } from 'src/app/shared/model/user-dto';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  private relativeUrlForAllUsers = '/users/all';
  private relativeUrlForDelete = '/users';
  private relativeUrlActivationOrDeactivation = '/users/activation-or-deactivation/';
  users: UserDto[];

  constructor(private genericService: GenericService, private toastr: ToastrService) {
    this.users = [];
  }

  ngOnInit() {
    this.getAllUsers();
  }

  getAllUsers() {
    this.genericService.get<UserDto[]>(this.relativeUrlForAllUsers).subscribe(
      (users: UserDto[]) => {
        this.users = users;
        this.toastr.success('Users loaded!');
      },
      (err) => {
        this.toastr.error('Problem with loading users!');
      }
    );
  }

  delete(id: number) {
    this.genericService.delete(this.relativeUrlForDelete, id).subscribe(
      () => {
          this.getAllUsers();
          this.toastr.success('Successfully deleted user!');
      },
      () => this.toastr.error('Delete was not successful')
    );
  }
  activateOrDeactivate(user: UserDto) {
    this.genericService.put(this.relativeUrlActivationOrDeactivation.concat('' + user.id),
                             user.userStatus !== 'ACTIVATED').subscribe(
      () => {
          if (user.userStatus === 'ACTIVATED') {
            this.toastr.success('Successfully deactivated user!');
            user.userStatus = 'DEACTIVATED';
          } else if (user.userStatus === 'DEACTIVATED') {
            user.userStatus = 'ACTIVATED';
            this.toastr.success('Successfully activated user!');
          }
      },
      () => this.toastr.error('De/Activation was not successful')
    );
  }

  editRoles(id: number) {
    this.toastr.info('TODO editRoles method');
  }

}
