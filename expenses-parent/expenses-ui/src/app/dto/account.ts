import { Operations } from './operation';

export class Account {
    id: number;
    name: string;
    owner: number;
    number: number;
    creationDate: Date;
    updateDate: Date;
    expenses: Operations;
}
export class Accounts extends Array<Account> {}