package SOLID_PRINCIPALS.Open_Closed;

class Payment{
    public void makePayment(String paymentMode, Double amount){
        if(paymentMode.equals("UPI")){
            upiPayment(amount);
        } else if (paymentMode.equals("CARD")) {
            cardPayment(amount);
        }
    }

    private void upiPayment(Double amount){
        System.out.println("Upi Payment done for rupees: " + amount);
    }
    private void cardPayment(Double amount){
        System.out.println("Card Payment done for rupees: " + amount);
    }
}
public class OCP_Violate {
    public static void main(String[] args) {
        Payment payment = new Payment();
        payment.makePayment("UPI", 90.89);
    }
}
