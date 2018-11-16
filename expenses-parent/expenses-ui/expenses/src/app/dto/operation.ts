import { Nomenclature } from './nomenclature';
import { Category } from './category';
import { TimeInterval } from 'rxjs';
import { OperationType } from './operation-type';

export class Operation {

    constructor() {
        this.nomenclature = new Nomenclature();
        this.category = new Category();
        this.interval = new Interval();
        this.discriminator = 'O';
        this.reimbursments = [];
    }

    id: number;
    beneficiary: any;
    emitter: any;
    discriminator: string;
    account: number;
    description: string;
    type: OperationType;
    amount: number;
    currency: string;
    effectiveDate: Date;
    operationTypeName: string;
    category: Category;
    nomenclature: Nomenclature;
    creationDate: Date;
    updateDate: Date;
    version: number;
    modifiable: boolean;
    interval?: Interval;
    reimbursments?: Refund[];

    children: Operations;
}
export class Operations extends Array<Operation> { }

export class Refund {
    id: number;
    reimbursmentDate: Date;
    reimbursedAmount: number;
}
export class Interval {
    interval: number;
    unit: number;
}
