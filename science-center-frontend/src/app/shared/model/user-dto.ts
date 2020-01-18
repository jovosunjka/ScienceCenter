import { Dto } from './dto';

export interface UserDto {
    id: number;
    username: string;
    firstName: string;
    lastName: string;
    email: string;
    city: string;
    country: string;
    scientificAreas: string;
    reviewer: boolean;
    userStatus: string;
    roles: Dto[];
}
