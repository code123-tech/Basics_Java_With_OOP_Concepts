package org.example.StructuredConcurrency;

// import java.util.concurrent.StructuredTaskScope;


/*
Add below configuration in pom.xml to enable this preview feature in Java 21

<build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <compilerArgs>--enable-preview</compilerArgs>
                </configuration>
            </plugin>
        </plugins>
    </build>

 */
public class StructuredConcurrencyExercise {

    record User(String userName) {}
    record Orders(int count) {}
    record Dashboard(User user, Orders orders) {}


    private static User fetchUser(int userId) throws InterruptedException {

        Thread.sleep(600);
        System.out.printf("User %s fetched by thread: %s%n", userId, Thread.currentThread());
        return new User("Alice");
    }

    private static Orders fetchOrders(int userId) throws InterruptedException {

        Thread.sleep(700);
        System.out.printf("Orders fetched for user %s by thread: %s%n", userId, Thread.currentThread());
        return new Orders(40);
    }

    private static Dashboard loadDashboard(int userId) throws Exception{

//        try(var scope = new StructuredTaskScope<Object>()){
//
//            StructuredTaskScope.Subtask<User> user = scope.fork(() -> fetchUser(userId));
//            StructuredTaskScope.Subtask<Orders> orders = scope.fork(() -> fetchOrders(userId));
//
//            scope.join()
//                    .close();
//
//            return new Dashboard(user.get(), orders.get());
//        }

        return new Dashboard(fetchUser(userId), fetchOrders(userId));
    }

    public static void main(String[] args) throws Exception {

        long currentTime = System.currentTimeMillis();
        Dashboard dashboard = loadDashboard(1); // userId is 1

        long elapsedTime = System.currentTimeMillis() - currentTime;
        System.out.println("Result: " + dashboard);
        System.out.println("Time   : " + elapsedTime + "ms (not 1300 ms) as running concurrently if used StructuredTaskScope");
    }

}
