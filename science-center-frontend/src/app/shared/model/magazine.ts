import { Dto } from './dto';

export interface Magazine {
    id: number;
    name: string;
    issn: string;
    currency: string;
    scientificAreas: string;
    mainEditor: string;
    editors: string;
    reviewers: string;
    paidUpTo?: string;
    plans?: Dto[];
}
