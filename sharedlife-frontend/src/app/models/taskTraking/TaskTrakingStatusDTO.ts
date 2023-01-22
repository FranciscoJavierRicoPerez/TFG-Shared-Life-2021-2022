import { TaskStatusDTO } from "./TaskStatusDTO";

export class TaskTrakingStatusDTO{
    taskStatusDTOList: TaskStatusDTO[];

    constructor(t?: TaskStatusDTO[]){
        this.taskStatusDTOList = t ?? [];
    }
}
