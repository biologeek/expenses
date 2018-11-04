import { Nomenclature } from './nomenclature';
import { Category } from './category';

export class Operation {

    id: number;
    beneficiary: any;
    emitter: any;
    discriminator: string;
    account: number;
    description: string;
    type: string;
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

    children: Operations;
}
export class Operations extends Array<Operation> { }