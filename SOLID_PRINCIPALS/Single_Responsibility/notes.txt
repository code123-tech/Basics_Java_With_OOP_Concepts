The Principle States that class/module/package should have single responsibility, It means it should have a single reason to be
changed.
For suppose, you have a payment system which makes payment, but along with it also sends email invoice to the person email id.
Here, Payment class has two reasons to be changed.

1. Logic change in payment/transaction
2. In the future, we can change logic of sendInvoiceToEmail method.
Thus, It violates the principle of Single Responsibility principle.

For getting rid of above issue, we can create another class which is only responsible for sending email and attachments.
Thus, we can follow Single responsibility principle.

https://medium.com/backticks-tildes/the-s-o-l-i-d-principles-in-pictures-b34ce2f1e898
https://blog.bitsrc.io/solid-principles-every-developer-should-know-b3bfa96bb688