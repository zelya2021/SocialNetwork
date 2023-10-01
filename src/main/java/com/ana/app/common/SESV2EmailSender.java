package com.ana.app.common;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
public class SESV2EmailSender {
    Region region = Region.US_WEST_2;
    SesClient ses = SesClient.builder().region(region).build();
    private final AppConfig appConfig;
    String recipientEmail;
    Integer resetPasswordCode;


    public SESV2EmailSender(AppConfig appConfig){
        this.appConfig = appConfig;
    }

    public void sendEmail(){
        try {
            var f = appConfig.getSenderEmail();
            var fg = appConfig.getSubject();
            SendEmailRequest request = SendEmailRequest.builder()
                    .destination(Destination.builder().toAddresses(recipientEmail).build())
                    .message(Message.builder()
                            .body(Body.builder().text(Content.builder().data(resetPasswordCode.toString()).charset("UTF-8").build()).build())
                            .subject(Content.builder().data(appConfig.getSubject()).charset("UTF-8").build())
                            .build())
                    .source(appConfig.getSenderEmail())
                    .build();

            SendEmailResponse response = ses.sendEmail(request);
            System.out.println("Email sent! Message ID: " + response.messageId());
        } catch (SesException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public Integer getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(Integer resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }
}

