export class User {
  id: string;//
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  username: string;
  //isOwner: boolean;
  roles: string[];

  constructor(i?: string, fn?: string, ln?: string, e?: string, p?: string, un?: string){
    this.id = i ?? ""; ////
    this.firstName = fn ?? "";
    this.lastName = ln ?? "";
    this.email = e ?? "";
    this.password = p ?? "";
    this.username = un ?? "";
    this.roles = [];
  }
}
