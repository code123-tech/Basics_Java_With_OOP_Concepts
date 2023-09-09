package DesignPatterns.ObserverDesignPattern.Observable;

import DesignPatterns.ObserverDesignPattern.Observer.NotificationAlertObserver;

import java.util.ArrayList;
import java.util.List;

public class IphoneInventoryObservable implements InventoryObservable{
    public List<NotificationAlertObserver> observers = new ArrayList<>();
    public int stockCount = 0;

    @Override
    public void addObserver(NotificationAlertObserver notificationAlertObserver) {
        observers.add(notificationAlertObserver);
    }

    @Override
    public void remove(NotificationAlertObserver notificationAlertObserver) {
        observers.remove(notificationAlertObserver);
    }

    @Override
    public void notifyObserver() {
        for(NotificationAlertObserver observer: observers){
            observer.update();
        }
    }

    @Override
    public void setCount(int count) {
        if(stockCount == 0 && count > 0){
            notifyObserver();
        }
        stockCount += count;
    }

    @Override
    public int getCount() {
        return stockCount;
    }
}
