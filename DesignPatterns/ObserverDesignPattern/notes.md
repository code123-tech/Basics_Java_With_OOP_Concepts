# Here we are dealing with Observer Design Pattern.

## Observer Pattern Basically consist of two main things, Observable(Subject: the thing which is being observed), 
## Observer(people who observe subject.)

### Here we are going to create notify system of shopping website, in which whenever an item goes into empty state, then 
### we will provide notify me functionality, so whenever that item is again comes on out shopping site, user (Subscribed)
### are able to get the notification either through mail or mobileNumber.

#### Let's identify the Subject, and Observer.

* Observer can be any user who has asked/subscribed for notify me on that product.
* So, as per the question user can subscribe on multiple products to be notified.
* So, Observable are also of multiple types, and observers are obviously multiple.
* So, Here Many-to-Many Relationship is going on.

* <u>Observable</u>: InventoryObservableInterface,    It will be implemented by multiple products Inventory like IphoneInventoryObservable,
* PumaShoesInventoryObservable etc.
* <u>Observer</u>: Now, we can have NotificationAlertObserver, Now user want to notify either using email or mobile or some other strategy.


