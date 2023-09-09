The Principle States that a class/module/package/method should be open for extension, not for modification.

For suppose, you have a payment system which makes payment for upi/card and based on payment mode you make payment with that
payment system.

1. Upi has makeUpiPayment method
2. Card has makeCardPayment method.

So, In future suppose, we want to extend this for Emi Payment, then we need to modify the existing class, which breaks the
Open Closed Principle as well as Single Responsibility.

For getting rid of above issue, we can create an interface which has makePayment method, and each paymentMode has its own
class which implements this interface.
