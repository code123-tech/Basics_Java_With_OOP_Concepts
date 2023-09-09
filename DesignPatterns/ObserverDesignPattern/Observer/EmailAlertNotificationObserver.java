package DesignPatterns.ObserverDesignPattern.Observer;

import DesignPatterns.ObserverDesignPattern.Observable.InventoryObservable;

public class EmailAlertNotificationObserver implements NotificationAlertObserver{
    String emailId;
    InventoryObservable inventoryObservable;

    public EmailAlertNotificationObserver(String emailId, InventoryObservable inventoryObservable){
        this.emailId = emailId;
        this.inventoryObservable = inventoryObservable;
    }
    @Override
    public void update() {
        sendMail(emailId, "Product is in stock, hurry up!!");
    }

    private void sendMail(String emailId, String msg){
        System.out.println("Mail sent to: " + emailId + " with message: " + msg);
    }
}
