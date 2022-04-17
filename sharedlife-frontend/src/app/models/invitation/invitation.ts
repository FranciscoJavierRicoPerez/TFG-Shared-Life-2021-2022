export class Invitation {
  username: string;
  idHome: string;
  homeCode: string;
  address: string;
  constructor(u: string, i: string, homeCode: string, address: string){
    this.username = u;
    this.idHome = i;
    this.homeCode = homeCode;
    this.address = address;
  }
}
