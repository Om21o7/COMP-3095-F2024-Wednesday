package ca.gbc.notificationservice.service;


import ca.gbc.notificationservice.event.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor

public class NotificationService {

    private final JavaMailSender javaMailSender;

    @KafkaListener(topics = "order-placed")
    public void listen(OrderPlacedEvent orderPlacedEvent) {

        log.info("Recevied message from order-placed topic {}",orderPlacedEvent);

        //Sending Email to customer
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("Comp3095@georgebrown.ca");
            messageHelper.setTo(orderPlacedEvent.getEmail());
            messageHelper.setSubject(String.format("Your Order %s was successfully placed", orderPlacedEvent.getOrderNumber()));
            messageHelper.setText(String.format("""
                    Good Day.
                    Your order with order number %s was successfully placed
                    Thank-you for your business
                    COMP3095 Staff
                    """, orderPlacedEvent. getOrderNumber ()
            ));
        };
        try {
            javaMailSender.send(messagePreparator);
            log.info("Order Notification Sent Successfully");
        }
        catch (Exception e) {
            log.error("Error occurred while sending email", e);
            throw new RuntimeException("Error occurred while attempting to send email", e);
        }


    }


}
