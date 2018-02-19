package net.macrosigma.general;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import net.macrosigma.general.ent.GmGesCorreo;

public class MailUtil {
	private static MailUtil instance;

	private Session mailSession;

	private MailUtil() {
		try (
				InputStream inputStream = this.getClass().getClassLoader()
				.getResourceAsStream("net"+File.separator+"macrosigma"+File.separator+"general"+File.separator+"mail.properties")) {

			final Properties configProperties = new Properties();

			configProperties.load(inputStream);
			final String username = configProperties.getProperty("user");
			final String pass = configProperties.getProperty("password");
			mailSession = Session.getInstance(configProperties,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username, pass);
						}
					});

		} catch (Exception e) {
			throw new RuntimeException(
					"No se pudo cargar propiedades del GmGesCorreo: " + e);
		}
	}

	/***
	 * Retorna una instancia singleton para el uso de este componente, cargando
	 * las configuraciones del archivo "mail.properties".
	 * 
	 * @return
	 */
	public static MailUtil getInstance() {
		if (instance == null) {
			synchronized (MailUtil.class) {
				if (instance == null) {
					instance = new MailUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * Envia un e-mail basado en el objeto GmGesCorreo pasado como parametro
	 * 
	 * @param GmGesCorreo
	 *            objeto que contiene la inforacion del remitente, destinatario,
	 *            titulo y contenido del GmGesCorreo
	 * @return retorna <code>true</code> si el GmGesCorreo se fue enviado
	 *         satisfactoriamente
	 */
	public boolean EnviarMail(GmGesCorreo GmGesCorreo) {
		try {

			Message message = new MimeMessage(mailSession);
			message.setFrom(new InternetAddress(GmGesCorreo.getDe()));
			if (GmGesCorreo.getCc()!=null) {
				message.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(GmGesCorreo.getCc()));
			}
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(GmGesCorreo.getPara()));
			message.setSubject(GmGesCorreo.getTitulo());
			message.setText(GmGesCorreo.getContenido());

			
			Transport transport = mailSession.getTransport("smtps");
            transport.connect();
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
//			Transport.send(message);

			return true;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

}
