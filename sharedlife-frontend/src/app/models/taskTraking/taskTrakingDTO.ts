export class TaskTrakingDTO{
    id: string;
    creationDate: string;
    startDate: string;
    endDate: string;
    constructor(i?: string, cd?: string, sd?: string, ed?:string){
        this.id = i ?? "";
        this.creationDate = cd ?? "";
        this.startDate = sd ?? "";
        this.endDate = ed ?? "";
    }

}