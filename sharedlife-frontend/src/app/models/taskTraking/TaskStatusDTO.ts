export class TaskStatusDTO {
    id: string;
    confirmed: boolean;

    constructor(i?: string, c?: boolean){
        this.id = i ?? "";
        this.confirmed = c ?? false;
    }
}