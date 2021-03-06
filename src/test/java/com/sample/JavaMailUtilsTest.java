package com.sample;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.FetchProfile;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

/**
 * Created by cjrequena on 16/08/16.
 */
@Log4j2
public class JavaMailUtilsTest {

    Properties properties;
    Folder inbox;
    Folder processed;
    //static final String EMAIL = "carlosjose.requena@outlook.com";
    //static final String PASSWORD = "";
    static final String EMAIL = "sshubtest2@tuitravel-ad.net";
    static final String PASSWORD = "Hola1234";
    static final String MAIL_SMTP_USER = EMAIL;
    static final String MAIL_SMTP_HOST = "smtp-mail.office365.com";
    //static final String MAIL_SMTP_HOST = "smtp-mail.outlook.com";


    static final String MAIL_SMTP_PORT = "587";
    static final String MAIL_SMTP_STARTTLS_ENABLE = "true";
    static final String MAIL_SMTP_AUTH = "true";
    static final String MAIL_SMTP_SOCKETFACTORY_PORT = "587";
    static final String MAIL_SMTP_SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
    static final String MAIL_SMTP_SOCKETFACTORY_FALLBACK = "true";

    static final String MAIL_IMAPS_SOCKETFACTORY_CLASS = "javax.net.ssl.SSLSocketFactory";
    static final String MAIL_IMAPS_HOST = "outlook.office365.com";
    //static final String MAIL_IMAPS_HOST = "imap-mail.outlook.com";
    static final String MAIL_IMAPS_PORT = "993";
    static final String MAIL_IMAPS_SOCKETFACTORY_PORT = "993";
    static final String MAIL_IMAPS_SOCKETFACTORY_FALLBACK = "false";

    @Before
    public void setUp() throws Exception {
        // Set manual Properties SMTP
        properties = System.getProperties();
        properties.setProperty("mail.smtp.user", MAIL_SMTP_USER);
        properties.setProperty("mail.smtp.host", MAIL_SMTP_HOST);
        properties.setProperty("mail.smtp.port", MAIL_SMTP_PORT);
        properties.setProperty("mail.smtp.starttls.enable", MAIL_SMTP_STARTTLS_ENABLE);
        properties.setProperty("mail.smtp.auth", MAIL_SMTP_AUTH);
        properties.setProperty("mail.smtp.socketFactory.port", MAIL_SMTP_SOCKETFACTORY_PORT);
        properties.setProperty("mail.smtp.socketFactory.class", MAIL_SMTP_SOCKETFACTORY_CLASS);
        properties.setProperty("mail.smtp.socketFactory.fallback", MAIL_SMTP_SOCKETFACTORY_FALLBACK);
        // Set manual Properties IMAP
        properties.setProperty("mail.imaps.socketFactory.class", MAIL_IMAPS_SOCKETFACTORY_CLASS);
        properties.setProperty("mail.imaps.host", MAIL_IMAPS_HOST);
        properties.setProperty("mail.imaps.port", MAIL_IMAPS_PORT);
        properties.setProperty("mail.imaps.socketFactory.port", MAIL_IMAPS_SOCKETFACTORY_PORT);
        properties.setProperty("mail.imaps.socketFactory.fallback", MAIL_IMAPS_SOCKETFACTORY_FALLBACK);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getSession() throws Exception {
        Session session = JavaMailUtils.getSession(EMAIL, PASSWORD, properties);
        Store store = session.getStore("imaps");
        store.connect();
        Assert.assertTrue(store.isConnected());
    }

    @Test
    public void getAllMessagesFromFolder() throws Exception {
        /**/
        Session session = JavaMailUtils.getSession(EMAIL, PASSWORD, properties);
        Store store = session.getStore("imaps");
        store.connect();

        /* Open the inbox using store. */
        inbox = store.getFolder("INBOX");
        inbox.open(Folder.READ_WRITE);

        /*Get messages*/
        Message messages[] =
            JavaMailUtils.getAllMessagesFromFolder(inbox, FetchProfile.Item.ENVELOPE, FetchProfile.Item.CONTENT_INFO, FetchProfile.Item.FLAGS,
                FetchProfile.Item.SIZE);
        Assert.assertNotNull(messages);
        Assert.assertTrue(messages.length == inbox.getMessageCount());
    }

    @Test
    public void getSeenMessagesFromFolder() throws Exception {

    }

    @Test
    public void getNotSeenMessagesFromFolder() throws Exception {

    }

    @Test
    public void getDraftMessagesFromFolder() throws Exception {

    }

    @Test
    public void getNotDraftMessagesFromFolder() throws Exception {

    }

    @Test
    public void sendEmail() throws Exception {

    }

    @Test
    public void sendAttachmentEmail() throws Exception {

    }

    @Test
    public void sendImageEmail() throws Exception {

    }

    @Test
    public void getAnsweredMessagesFromFolder() throws Exception {

    }

    @Test
    public void getNotAnsweredMessagesFromFolder() throws Exception {

    }

    @Test
    public void getFlaggedMessagesFromFolder() throws Exception {

    }

    @Test
    public void getNotFlaggedMessagesFromFolder() throws Exception {

    }

    @Test
    public void getRecentMessagesFromFolder() throws Exception {

    }

    @Test
    public void getNotRecentFlaggedMessagesFromFolder() throws Exception {

    }

    @Test
    public void getDeletedMessagesFromFolder() throws Exception {

    }

    @Test
    public void getNotDeletedFlaggedMessagesFromFolder() throws Exception {

    }

    @Test
    public void copyMessages() throws Exception {

    }

    @Test
    public void moveMessages() throws Exception {

    }

    @Test
    public void JavaMailUtilsTest() {
        try {
            Session session = JavaMailUtils.getSession(EMAIL, PASSWORD, properties);
            Store store = session.getStore("imaps");
            store.connect();

            //JavaMailUtils.sendEmail(session, "no-replay@gmail.com", EMAIL, "no-replay@gmail.com", "TEST", "UN BODY TEST");

            /* Open the inbox using store. */
            inbox = store.getFolder("INBOX");
            inbox.open(Folder.READ_WRITE);

            Message messages[] = JavaMailUtils
                .getAllMessagesFromFolder(inbox, FetchProfile.Item.ENVELOPE, FetchProfile.Item.CONTENT_INFO, FetchProfile.Item.FLAGS,
                    FetchProfile.Item.SIZE);
            //Message messages[] = JavaMailUtils.getSeenMessagesFromFolder(inbox, FetchProfile.Item.ENVELOPE, FetchProfile.Item.CONTENT_INFO, FetchProfile.Item.FLAGS, FetchProfile.Item.SIZE);

            Message message = messages[0];
            Object content = message.getContent();

            if (content instanceof String) {
                String body = (String) content;
            } else if (content instanceof Multipart) {
                parseMultipart((Multipart) content);
            }

            /* Open the processed using store. */
            processed = store.getFolder("PROCESSED");
            processed.open(Folder.READ_WRITE);

            /*Move messges from a folder to another folder*/
            //JavaMailUtils.moveMessages(inbox, processed, messages);


            try {
                printAllMessages(messages);
                inbox.close(true);
                store.close();
            } catch (Exception ex) {
                log.debug("Exception arise at the time of read mail");
                ex.printStackTrace();
            }

        } catch (MessagingException e) {
            log.debug("Exception while connecting to server: " + e.getLocalizedMessage());
            e.printStackTrace();
            System.exit(2);
        } catch (Exception ex) {
            log.error(ex);
        }


    }

    // Parse the part to find the body
    public void parsePart(Part part) throws MessagingException, IOException {
        if (part.isMimeType("text/plain") && part.getFileName() == null) {
            System.out.println("text/plain " + part.getContentType());
            System.out.println(part.getContent());
            System.out.println("---------------------------------");
        } else if (part.isMimeType("text/html") && part.getFileName() == null) {
            System.out.println("text/html " + part.getContentType());
            System.out.println(part.getContent());
            System.out.println("---------------------------------");
        } else if (part.isMimeType("image/*")) {
            System.out.println("Image " + part.getContentType());
            System.out.println("File=" + part.getFileName());
            System.out.println("---------------------------------");
        } else {
            Object contentPart = part.getContent();
            if (part.getContent() instanceof String && part.getFileName() == null) {
                log.debug("");
            } else if (contentPart instanceof InputStream) {
                log.debug("");
            } else {
                log.debug("");
            }
        }
    }

    // Parse the Multipart to find the body
    public void parseMultipart(Multipart multipart) {
        try {
            // Loop through all of the BodyPart's
            for (int i = 0; i < multipart.getCount(); i++) {
                // Grab the body part
                BodyPart bodyPart = multipart.getBodyPart(i);
                // Grab the disposition for attachments
                String disposition = bodyPart.getDisposition();

                // It's not an attachment
                if (disposition == null && bodyPart instanceof MimeBodyPart) {
                    MimeBodyPart mimeBodyPart = (MimeBodyPart) bodyPart;
                    if (mimeBodyPart.getContent() instanceof Multipart) {
                        // Use recursion to parse the sub-Multipart
                        parseMultipart((Multipart) mimeBodyPart.getContent());
                    } else {
                        parsePart(mimeBodyPart);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printAllMessages(Message[] msgs) throws Exception {
        for (int i = 0; i < msgs.length; i++) {
            log.debug("MESSAGE #" + (i + 1) + ":");
            printEnvelope(msgs[i]);
        }
    }

    public void printEnvelope(Message message) throws Exception {

        Address[] a;

        // FROM
        if ((a = message.getFrom()) != null) {
            for (int j = 0; j < a.length; j++) {
                log.debug("De : " + a[j].toString());
            }
        }

        String subject = message.getSubject();

        Date receivedDate = message.getReceivedDate();
        Date sentDate = message.getSentDate(); // receivedDate is returning
        // null. So used getSentDate()

        //Dar Formato a la fecha
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        log.debug("Asunto : " + subject);

        if (receivedDate != null) {
            log.debug("Recibido: " + df.format(receivedDate));
        }

        log.debug("Enviado : " + df.format(sentDate));
    }

}
