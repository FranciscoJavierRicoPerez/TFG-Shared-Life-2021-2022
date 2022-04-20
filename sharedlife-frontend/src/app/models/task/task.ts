export class Task {
  id: string;
  title: string;
  description: string;
  startDate: string;
  endDate: string;
  finished: boolean;
  user: string;
  idHome: string;

  constructor(i: string, t: string, d: string, sd: string, ed: string, f:boolean, u:string, idHome:string){
    this.id = i;
    this.title = t;
    this.description = d;
    this.startDate = sd;
    this.endDate = ed;
    this.finished = f;
    this.user = u;
    this.idHome = idHome;
  }
}
