package edu.performance.test.mailoperation;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.AuthenticationFailedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import edu.performance.test.Library;
import edu.performance.test.PerformanceTest;
import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.filesequentialoperation.FileSequentialOperation;

/**
 * This class extends AsyncTask and it sends downloading files from oi website.
 * This Class do not get features from PerformanceTest and it has a attribute
 * named level. Operations like that must be implemented using background tasks.
 * 
 * @author Thiago
 */
public class MailOperation extends PerformanceTest<String> {
	private final int NOT_ID = 7;
	private String to = "";
	private String text;

	public MailOperation(PerformanceTestActivity activity, String level, String to, String text) {
		super(level, activity);
		this.to = to;
		this.text = text;
		activity.executeTest();

	}

	/**
	 * This method sends email from t.library@ymail.com to desirable email
	 * recipient. In this method level determines the number of e-mail will be
	 * send.
	 * 
	 * @param level
	 *            Defines the name file will be attached in email which will be
	 *            send.
	 * @param to
	 *            Determines the email that will be the test email(s).
	 */

	void testAMailOperation(String level, String to) {

		String host = "smtp.mail.yahoo.com";
		String port = "587";
		// boolean auth = true;
		// String smtpPort = "465";
		Properties props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", "false");
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.starttls.enable", "true");
		final String username = "t.library@yahoo.com.br", password = "Performance1";
		// props.put("mail.smtp.user", username);
		// props.put("mail.smtp.password", password);

		if (((MailOperationActivity) activity).isNotify()) {
			NotificationManager notifMgr = (NotificationManager) activity
					.getApplicationContext().getSystemService(
							Context.NOTIFICATION_SERVICE);

			NotificationCompat.Builder builder = new NotificationCompat.Builder(
					activity.getApplicationContext());
			builder.setSmallIcon(R.drawable.ic_notify_check_mail);
			builder.setWhen(System.currentTimeMillis());
			builder.setOngoing(true);

			builder.setTicker("Sending mail");

			builder.setContentTitle("Sending to: " + to);
			builder.setContentText("From: " + username);

			TaskStackBuilder stack = TaskStackBuilder.create(activity
					.getApplicationContext());
			// account.getInboxFolderName());
			stack.addParentStack(Library.class);
			stack.addNextIntent(new Intent(activity.getApplicationContext(),
					MailOperationActivity.class));
			builder.setContentIntent(stack.getPendingIntent(0, 0));
			notifMgr.notify(NOT_ID, builder.build());
		}

		Session session = null;
		try {
			session = Session.getDefaultInstance(props,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(username,
									password);
						}

					});
			session.setDebug(false);
		}

		catch (Exception e) {
			Bundle extras = new Bundle();
			extras.putString(PerformanceTestActivity.ERROR_MESSAGE, (e.getMessage()));
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			activity.finishTest(extras);
		}

		if (session != null) {
			try {
				Message message = new MimeMessage(session);
				// message.
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to));
				message.setSubject("TestLibrary definir level depois");
				final MimeBodyPart messageBodyPart = new MimeBodyPart();
				new Thread(new Runnable() {
					
		            @Override
		            public void run() {
		            	try {
		            		if(new File(text).isFile())
							messageBodyPart.setContent(new FileSequentialOperation().testTJMreadSequentialAcessFile(text), "text/html");
		            		else
		            			messageBodyPart.setContent(text, "text");
						} catch (MessagingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
		            }
		        }).start();
		        
		        Multipart multipart = new MimeMultipart();
		        multipart.addBodyPart(messageBodyPart);
		        
				if (level != null && !level.isEmpty()) {
					// creates multi-part				

					MimeBodyPart attachPart = new MimeBodyPart();

					attachPart.attachFile(level);

					multipart.addBodyPart(attachPart);


				}
				message.setContent(multipart);
				Transport.send(message);

				

			}
			catch(AuthenticationFailedException afe){
				
			}
			catch (MessagingException e) {
				throw new RuntimeException(e);
				

			} catch (IOException e) {
				System.err.println("Erro ao tentar carregar o arquivo: " + level);
				e.printStackTrace();
			}
			finally{
				if (((MailOperationActivity) activity).isNotify()) {
					NotificationManager notifMgr = (NotificationManager) activity
							.getApplicationContext().getSystemService(
									Context.NOTIFICATION_SERVICE);

					notifMgr.cancel(NOT_ID);
				}
			}

		} else {
Bundle extras = new Bundle();
			activity.setResult(PerformanceTestActivity.RESULT_CANCELED);
			extras.putString(PerformanceTestActivity.ERROR_MESSAGE,"Exceção: Não foi possível abrir uma sessão para envio. Algum erro está ocorrendo com o e-mail!");
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			activity.finishTest(extras);
		}
	}

	public void execute() {

		try {
			testAMailOperation(this.getLevel(), to);


		} catch (RuntimeException ae) {
			Bundle extras = new Bundle();
			//System.out.println(ae.getMessage());
			activity.setResult(PerformanceTestActivity.RESULT_CANCELED);
			extras.putString(PerformanceTestActivity.ERROR_MESSAGE,"Exceção na execução. Algum erro está ocorrendo com o e-mail! " + ((ae !=null && ae.getMessage() != null)?ae.getMessage() : "Runtime exception!"));
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			activity.finishTest(extras);
		}

		Bundle extras = new Bundle();
		activity.setResult(PerformanceTestActivity.RESULT_OK);
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
	}

}
