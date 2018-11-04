import { Accounts } from './account';

export class UserImage {

    id: number;
    username: string;
    firstName: string;
    lastName: string;
    age: number;
    mailAddress: string;
    phoneNumber: string;
    accounts: Accounts;
    sessionToken: string;
}
