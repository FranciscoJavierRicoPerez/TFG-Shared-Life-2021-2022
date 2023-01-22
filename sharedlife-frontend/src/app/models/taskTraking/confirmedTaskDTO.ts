import { User } from "../user/user";

export class ConfirmedTaskDTO{
    username: string;
    idTask: string;
    members: User[];
    constructor(i?: string, u?: string, h?: User[]){
        this.idTask = i ?? "";
        this.username = u ?? "";
        this.members = h ?? [];
    }

}