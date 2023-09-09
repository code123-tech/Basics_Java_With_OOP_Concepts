package DesignPatterns.ObserverDesignPattern.Observable;

import DesignPatterns.ObserverDesignPattern.Observer.NotificationAlertObserver;

public interface InventoryObservable {
    public void addObserver(NotificationAlertObserver notificationAlertObserver);
    public void remove(NotificationAlertObserver notificationAlertObserver);
    public void notifyObserver();
    public void setCount(int count);
    public int getCount();
}
