import { ScientificPaperForSearchResult } from './scientific-paper-for-search-result';

export interface ResultData {
    title: string;
    keywords: string;
    location: string;
    highlight: string;
    scientificPaper: ScientificPaperForSearchResult;
}
