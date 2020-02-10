export interface ScientificPaper {
    taskId?: string;
    id?: number;
    title: string;
    keywords: string;
    scientificPaperAbstract: string;
    scientificArea: string;
    author: string;
    coauthors: string;
    commentForAuthor?: string;
    commentForEditor?: string;
    answers?: string;
}
