package DesignPatterns.ObserverDesignPattern.Observer;

import DesignPatterns.ObserverDesignPattern.Observable.InventoryObservable;

public class MobileAlertNotificationObserver implements NotificationAlertObserver{
    String mobileNo;
    InventoryObservable inventoryObservable;

    public MobileAlertNotificationObserver(String mobileNo, InventoryObservable inventoryObservable){
        this.mobileNo = mobileNo;
        this.inventoryObservable = inventoryObservable;
    }
    @Override
    public void update() {
        sendMessage(mobileNo, "Product is in stock, hurry up!!");
    }

    private void sendMessage(String mobileNo, String msg){
        System.out.println("Message sent to: " + mobileNo + " with message: " + msg);
    }
}
