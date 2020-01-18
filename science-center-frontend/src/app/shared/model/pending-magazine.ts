import { Magazine } from './magazine';

export interface PendingMagazine extends Magazine {
    taskId: string;
}
