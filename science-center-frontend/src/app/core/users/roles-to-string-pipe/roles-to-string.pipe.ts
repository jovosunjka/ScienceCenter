import { Pipe, PipeTransform } from '@angular/core';
import { Dto } from 'src/app/shared/model/dto';

@Pipe({
  name: 'rolesToString'
})
export class RolesToStringPipe implements PipeTransform {

  transform(value: Dto[]): string {
    return value.map(role => role.name).join(', ');
  }

}
