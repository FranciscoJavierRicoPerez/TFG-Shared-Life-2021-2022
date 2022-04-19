export class Task {
  title: string;
  description: string;
  startDate: string;
  endDate: string;
  finished: boolean;
  user: string;

  constructor(t: string, d: string, sd: string, ed: string, f:boolean, u:string){
    this.title = t;
    this.description = d;
    this.startDate = sd;
    this.endDate = ed;
    this.finished = f;
    this.user = u;
  }
}
