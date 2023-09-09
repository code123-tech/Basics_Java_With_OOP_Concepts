package SOLID_PRINCIPALS.Single_Responsibility;

class EmailSender{
    public void sendInvoiceToEmail(String to, String anyAttachment){
        System.out.println("Invoice attachment is sent to: " + to);
    }
}
class Payment{
    EmailSender emailSender = new EmailSender();
    public void makePayment(String paymentMode, Double rupees){
        System.out.println("Payment is done through " + paymentMode + " for rupees: " + rupees);
        emailSender.sendInvoiceToEmail("abc@gmail.com", "emem");
    }
}

public class SRP_Follows {
    public static void main(String[] args) {
        Payment payment = new Payment();
        payment.makePayment("UPI", 13.90);
    }
}
