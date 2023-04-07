export class PasswordUpdateDTO {
    email: string;
    actualPassword: string;
    newPassword: string;

    constructor(e: string, ap: string, np: string){
        this.email = e ?? "";
        this.actualPassword = ap ?? "";
        this.newPassword = np ?? "";
    }
}