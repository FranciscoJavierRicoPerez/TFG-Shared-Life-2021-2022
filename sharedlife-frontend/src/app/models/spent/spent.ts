export class Spent {
  id: string;
  title: string;
  description: string;
  totalPrice: string;
  userToPay: string; // nombre del usuario al que hay que pagar (EL QUE CREA EL GASTO)
  idHome: string;
  paid: boolean
  constructor(i?: string, t?: string, d?: string, tp?: string, utp?: string, idHome?: string, p?: boolean){
    this.id = i ?? "";
    this.title = t ?? "";
    this.description = d ?? "";
    this.totalPrice = tp ?? "";
    this.userToPay = utp ?? "";
    this.idHome = idHome ?? "";
    this.paid = p ?? false;
  }

}
