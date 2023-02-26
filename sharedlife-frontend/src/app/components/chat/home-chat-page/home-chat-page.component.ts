import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Console } from 'console';
import { MessageDTO } from 'src/app/models/Message/MessageDTO';
import { ChatService } from 'src/app/services/chat/chat.service';
import { HomeService } from 'src/app/services/home/home.service';
import { TokenService } from 'src/app/services/token/token.service';

@Component({
  selector: 'app-home-chat-page',
  templateUrl: './home-chat-page.component.html',
  styleUrls: ['./home-chat-page.component.css']
})
export class HomeChatPageComponent implements OnInit {

  username: string = "";
  messagesDTO: MessageDTO[] = [];
  messageDTO: MessageDTO;
  newMessage = '';
  homeAddress: string = "";
  idHome: string = "";

  constructor(
    private TokenService: TokenService,
    private ChatSevice: ChatService,
    private HomeService: HomeService,
    private ActivatedRoute: ActivatedRoute
  ) { }

  ngOnInit(): void {
    if(this.TokenService.getToken()){
      this.username = this.TokenService.getUserName();
      this.idHome = this.ActivatedRoute.snapshot.params['id'];
      this.HomeService.getHomeById(this.idHome).subscribe((data) => {
        this.homeAddress = data.address;
      });

      console.log(this.messagesDTO.length);
      this.ChatSevice.getNewMessage().subscribe((message: string) => {
        this.messageDTO = new MessageDTO(message.split(':')[0], message.split(':')[1]);
        if(this.messageDTO.ownerName !== ''){
          this.messagesDTO.push(this.messageDTO);
        }
      });
    }
  }

  sendMessage() {
    console.log('hola')
    if(this.newMessage !== ''){
      this.ChatSevice.sendMessage(this.newMessage, this.username);
    }
    this.newMessage = '';
  }

  /* getMessages(){
    this.messagesDTO = [
      new MessageDTO("Francisco", "Chicos el proximo Lunes pasare a por el alquiler"),
      new MessageDTO("Ainhoa", "Vale perfecto yo estare para dartelo"),
      new MessageDTO("Dylan", "Yo no voy a estar, pero graciass!!"),
      new MessageDTO("Eira", "Yo tampoco estare, gracias Fran."),
      new MessageDTO("Ainhoa", "A que hora vendras?"),
      new MessageDTO("Francisco", "Aproximadamente a las 20:00 mas o menos"),
      new MessageDTO("Nereida", "Creo que yo también podre estar sin problemas")
    ]
    return this.messagesDTO;
  } */




}
