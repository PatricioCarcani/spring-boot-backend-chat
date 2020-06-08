package ar.com.karkanis.springboot.backend.chat.controllers;

import java.util.Date;
import java.util.Random;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import ar.com.karkanis.springboot.backend.chat.models.documents.Mensaje;

@Controller
public class ChatController {
	
	// to do: automatizar, quizas con valores RGB aleatorios
	private String[] colores = {"red", "blue", "green", "magenta", "orange", "purple", "black", "dark grey", "aqua", "blueviolet", "brown", "cornflowerblue", "darkseagreen"};
	
	@MessageMapping("/mensaje") // app/mensaje (tiene el prefijo en WebSocketConfig)
	@SendTo("/chat/mensaje") // 
	public Mensaje recibeMensaje(Mensaje mensaje) {
		
		if(mensaje.getTipo().equals("NUEVO_USUARIO")) {
			mensaje.setColor(colores[new Random().nextInt(colores.length)]);
			mensaje.setTexto("¡" + mensaje.getUsername() + " se ha conectado!");
		}
		mensaje.setFecha(new Date().getTime());
		//mensaje.setTexto("Recibido por el broker: " + mensaje.getTexto());
		
		return mensaje;
		
	}
	
	@MessageMapping("/escribiendo")
	@SendTo("/chat/escribiendo")
	public String estaEscribiento(String username) {
		return username.concat(" está escribiendo...");
	}

}
