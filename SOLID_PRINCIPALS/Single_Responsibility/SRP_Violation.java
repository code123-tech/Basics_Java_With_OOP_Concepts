package SOLID_PRINCIPALS.Single_Responsibility;

class Payment1 {
    public void makePayment(String paymentMode, Double rupees){
        System.out.println("Payment is done through " + paymentMode + " for rupees: " + rupees);
        emailInvoice();
    }
    private void emailInvoice(){
        System.out.println("Invoice is sent..");
    }
}
public class SRP_Violation {
    public static void main(String[] args) {
        Payment1 payment = new Payment1();
        payment.makePayment("UPI", 13.90);
    }
}
