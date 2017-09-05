package app.web.administration;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import app.web.security.SecurityPageBean;

public class Controlador {
	private static final Logger log = LogManager.getLogger(SecurityPageBean.class.getName());
	
	
	public boolean enviarCorreo(Correo c){
		log.debug("Begin - SendMessage");
		try{
			Properties p = new Properties();
			p.put("mail.smtp.host", "smtP.gmail.com");
			p.setProperty("mail.smtp.starttls.enable", "true");
			p.setProperty("mail.smtp.port", "587");
			p.setProperty("mail.smtp.user", c.getUsuarioCorreo());
			p.setProperty("mail.smtp.auth", "true");
			
			Session s = Session.getDefaultInstance(p,null);
			BodyPart texto = new MimeBodyPart();
			texto.setText(c.getMensaje());
			BodyPart adjunto = new MimeBodyPart();
			
//			if (!c.getRutaArchivo().equals("")){
//				adjunto.setDataHandler(new DataHandler(new FileDataSource(c.getRutaArchivo())));
//				adjunto.setFileName(c.getNombreArchivo());
//			}
			MimeMultipart m = new MimeMultipart();
			m.addBodyPart(texto);
			
//			if(!c.getRutaArchivo().equals("")) {
//				m.addBodyPart(adjunto);
//			}
			
			MimeMessage mensaje = new MimeMessage(s);
			mensaje.setFrom(new InternetAddress(c.getUsuarioCorreo()));
			mensaje.addRecipient(Message.RecipientType.TO, new InternetAddress(c.getDestino()));
			mensaje.setSubject(c.getAsunto());
			mensaje.setContent(m);
			
			Transport t = s.getTransport("smtp");
			t.connect(c.getUsuarioCorreo(), c.getContrasenia());
			t.sendMessage(mensaje, mensaje.getAllRecipients());
			t.close();
			return true;
						
		} catch(Exception e) {
			log.error("Error to send message : ", e);                                                                         
			return false;
		}
		
	}
}
