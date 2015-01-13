package edu.performance.test.mailoperation;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
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

	public MailOperation(PerformanceTestActivity activity, String level, String to) {
		super(level, activity);
		this.to = to;
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
			System.out.println(e.getMessage());
		}

		if (session != null) {
			try {
				Message message = new MimeMessage(session);
				// message.
				message.setFrom(new InternetAddress("t.library@yahoo.com.br"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse(to));
				message.setSubject("Level TestLibrary");
				message.setText("This is a email created to test,"
						+ "\n\n No spam to my email, please!");

				MimeBodyPart messageBodyPart = new MimeBodyPart();

				Multipart multipart = new MimeMultipart();

				messageBodyPart = new MimeBodyPart();
				String file = level;
				String fileName = "attachmentName.txt";
				DataSource source = new FileDataSource(file);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName);
				multipart.addBodyPart(messageBodyPart);

				message.setContent(multipart);

				Transport.send(message);

				if (((MailOperationActivity) activity).isNotify()) {
					NotificationManager notifMgr = (NotificationManager) activity
							.getApplicationContext().getSystemService(
									Context.NOTIFICATION_SERVICE);

					notifMgr.cancel(NOT_ID);
				}

			}

			catch (MessagingException e) {
				throw new RuntimeException(e);

			}

		} else {
			System.out.println("Null session");
		}
	}

	public void execute() {

		try {
			testAMailOperation(this.getLevel(), to);
			// new
			// Send(activity.getApplicationContext()).execute(this.getLevel(),
			// "thiago.soares@ymail.com");

		} catch (RuntimeException ae) {
			Bundle extras = new Bundle();
			System.out.println(ae.getMessage());
			extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, false);
			activity.finishTest(extras);
		}

		Bundle extras = new Bundle();
		extras.putBoolean(PerformanceTestActivity.RESULT_WAS_OK, true);
		activity.finishTest(extras);
	}

}
