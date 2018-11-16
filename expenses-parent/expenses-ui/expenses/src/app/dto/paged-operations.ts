import { Operations } from './operation';

export class PagedOperations {
    operations: Operations;
    totalOperations: number;
    totalPages: number;
    operationPerPage: number;
    currentPage: number;
}
