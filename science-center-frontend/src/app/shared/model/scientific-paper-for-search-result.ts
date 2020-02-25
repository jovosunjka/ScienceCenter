import { Dto } from './dto';

export interface ScientificPaperForSearchResult {
    id: number;
    magazineName: string;
    scientificPaperAbstract: string;
    scientificArea: string;
    author: string;
    coauthors: string;
    paidUpTo: string;
    plans: Dto[];
}
