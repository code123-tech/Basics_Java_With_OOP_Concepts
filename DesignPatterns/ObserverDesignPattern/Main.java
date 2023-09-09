package DesignPatterns.ObserverDesignPattern;

import DesignPatterns.ObserverDesignPattern.Observable.InventoryObservable;
import DesignPatterns.ObserverDesignPattern.Observable.IphoneInventoryObservable;
import DesignPatterns.ObserverDesignPattern.Observer.EmailAlertNotificationObserver;
import DesignPatterns.ObserverDesignPattern.Observer.MobileAlertNotificationObserver;
import DesignPatterns.ObserverDesignPattern.Observer.NotificationAlertObserver;

public class Main {
    public static void main(String[] args) {
        InventoryObservable iphoneInventoryObservable = new IphoneInventoryObservable();

        NotificationAlertObserver observer1 = new EmailAlertNotificationObserver("swap@gmail.com", iphoneInventoryObservable);
        NotificationAlertObserver observer2 = new EmailAlertNotificationObserver("dd@gmail.com", iphoneInventoryObservable);
        NotificationAlertObserver observer3 = new MobileAlertNotificationObserver("+919890908909", iphoneInventoryObservable);

        iphoneInventoryObservable.addObserver(observer1);
        iphoneInventoryObservable.addObserver(observer2);
        iphoneInventoryObservable.addObserver(observer3);

        iphoneInventoryObservable.setCount(10);
    }
}
