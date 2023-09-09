package SOLID_PRINCIPALS.Open_Closed;

interface Transaction{
    void makeTransaction(Double amount);
}

class UPITransaction implements Transaction{
    @Override
    public void makeTransaction(Double amount) {
        System.out.println("Upi Payment done for rupees: " + amount);
    }
}

class CARDTransaction implements Transaction{
    @Override
    public void makeTransaction(Double amount) {
        System.out.println("Card Payment done for rupees: " + amount);
    }
}

class EMITransaction implements Transaction{
    @Override
    public void makeTransaction(Double amount) {
        System.out.println("EMI Payment done for rupees: " + amount);
    }
}
class Factory{
    private static Transaction cardTransaction = new CARDTransaction();
    private static Transaction upiTransaction = new UPITransaction();
    private static Transaction emiTransaction = new EMITransaction();

    public static Transaction getTransactionType(String paymentMode){
        if(paymentMode.equals("UPI")){
            return upiTransaction;
        }
        if (paymentMode.equals("CARD")){
            return cardTransaction;
        }
        if (paymentMode.equals("EMI")){
            return emiTransaction;
        }
        return null;
    }
}

class Payment1{
    public void makePayment(String paymentMode, Double amount){
        // Use factory pattern to find Transaction type object, Using
        Factory.getTransactionType(paymentMode).makeTransaction(amount);
    }
}
public class OCP_Follows {
    public static void main(String[] args) {
        Payment1 payment1 = new Payment1();
        payment1.makePayment("EMI", 45.67);
    }
}
