export class MessageDTO {
    message: string;
    ownerName: string;
    
    constructor(on?: string, u?: string){
        this.message = u ?? "";
        this.ownerName = on ?? "";
    }

}