import { User } from "../user/user";

export class DebtInfo {
    debtUser: User | null;
    paid: boolean;

    constructor(user?: User, paid?: boolean) {
        this.debtUser = user ?? null;
        this.paid = paid ?? false;
    }
  
}
  