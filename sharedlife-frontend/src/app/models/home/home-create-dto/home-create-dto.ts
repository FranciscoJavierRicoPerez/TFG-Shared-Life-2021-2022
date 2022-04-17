export class HomeCreateDTO {
  id: string;
  address!: string;
  floor!: string;
  number!: string;
  city!: string;
  country!: string;
  rooms!: string;
  token: string;
  constructor(a: string, f: string, n: string, ct: string, co: string, r: string, t: string){
    this.address=a;
    this.floor=f;
    this.number=n;
    this.city=ct;
    this.country=co;
    this.rooms=r;
    this.token=t;
  }
}
