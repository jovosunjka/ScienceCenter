import { ReviewingResult } from './reviewing-result';

export interface ScientificPaperWithReviewings {
    taskId: string;
    title: string;
    keywords: string;
    scientificPaperAbstract: string;
    scientificArea: string;
    author: string;
    coauthors: string;
    reviewings: ReviewingResult[];
    commentForAuthor?: string;
    answers?: string;
}
