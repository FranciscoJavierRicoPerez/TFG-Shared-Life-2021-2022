export class Debt {
  id: string;
  idHome: string;
  idUser: string;
  idSpent: string;
  paid: boolean;
  pricePerPerson: string;
  userToPay: string;

  constructor(i: string, ih: string, iu: string, is: string, p: boolean, ppp: string, utp: string){
    this.id = i;
    this.idHome = ih;
    this.idUser = iu;
    this.idSpent = is;
    this.paid = p;
    this.pricePerPerson = ppp;
    this.userToPay = utp;
  }

}
