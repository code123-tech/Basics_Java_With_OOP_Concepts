package org.example.ScopedValues;

//import java.lang.ScopedValue;

/**
 * Focus Area: Scoped Values — The "Context Carrier"
 *
 * <p>
 *   Exercise: Pass TenantID and UserID from a main thread down into a deep
 *   call stack without passing them as method parameters.
 * </p>
 *
 * Parts:
 * <ul>
 *     <li>Part 1 — ThreadLocal approach (old way, problematic with virtual threads)</li>
 *     <li>Part 2 - ScopedValue approach (new way, designed with virtual threads)</li>
 *     <li>Part 3 - ScopedValue with Virtual Threads approach (contrast with ThreadLocal)</li>
 * </ul>
 */
/*
  Add below configuration in pom.xml to enable preview feature in Java 21:
  <pre>
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
  </pre>
 */
public class ScopedValuesExercise {

    // =========================================================================
    // PART 1: ThreadLocal Approach (Old Way)
    // =========================================================================
    static class ThreadLocalApproach {

        private static final ThreadLocal<String> TENANT_ID = new ThreadLocal<>();
        private static final ThreadLocal<String> USER_ID = new ThreadLocal<>();

        // Level 1: Entry point
        static void handleRequest(String tenantId, String userId) {

            TENANT_ID.set(tenantId);
            USER_ID.set(userId);

            try {
                System.out.println("[ThreadLocal] Request received — Thread: " + Thread.currentThread().getName());
                serviceLayer();
            } finally {
                // Must manually clean up — forgetting this causes memory leaks in thread pools
                 TENANT_ID.remove();
                 USER_ID.remove();
            }
        }

        // Level 2: service layer
        static void serviceLayer() {

            System.out.println("[ThreadLocalApproach] serviceLayer  - Tenant: "  + TENANT_ID.get() + ", UserId: " + USER_ID.get());
             repositoryLayer();
        }

        static void repositoryLayer() {

            System.out.println("[ThreadLocalApproach] repositoryLayer  - Tenant: "  + TENANT_ID.get() + ", UserId: " + USER_ID.get());
             auditLayer();
        }

        static void auditLayer() {

            System.out.println("[ThreadLocalApproach] auditLayer  - Tenant: "  + TENANT_ID.get() + ", UserId: " + USER_ID.get());
            System.out.println("[ThreadLocal] Audit log written for tenant: " + TENANT_ID.get());
        }

    }

    // =========================================================================
    // PART 2: ScopedValue Approach (New Way - Java 21 preview)
    // =========================================================================
//    static class ScopedValueApproach{
//
//        private static final ScopedValue<String> TENANT_ID = ScopedValue.newInstance();
//        private static final ScopedValue<String> USER_ID = ScopedValue.newInstance();
//
//        // Level 1: Entry point
//        static void handleRequest(String tenantId, String userId) {
//
//            ScopedValue.where(TENANT_ID, tenantId)
//                    .where(USER_ID, userId)
//                    .run(() -> {
//                        System.out.println("[ScopedValueApproach] Request received — Thread: " + Thread.currentThread().getName());
//                        serviceLayer();
//                    });
//
//            // No cleanup needed — automatically unbound when run() exits
//        }
//
//        // Level 2: service layer
//        static void serviceLayer() {
//
//            System.out.println("[ScopedValueApproach] serviceLayer  - Tenant: "  + TENANT_ID.get() + ", UserId: " + USER_ID.get());
//            repositoryLayer();
//        }
//
//        static void repositoryLayer() {
//
//            System.out.println("[ScopedValueApproach] repositoryLayer  - Tenant: "  + TENANT_ID.get() + ", UserId: " + USER_ID.get());
//            auditLayer();
//        }
//
//        static void auditLayer() {
//
//            System.out.println("[ScopedValueApproach] auditLayer  - Tenant: "  + TENANT_ID.get() + ", UserId: " + USER_ID.get());
//            System.out.println("[ScopedValue] Audit log written for tenant: " + TENANT_ID.get());
//        }
//
//    }

    // =========================================================================
    // PART 3: ScopedValue With Virtual Threads
    // =========================================================================
//    static class VirtualThreadComparison {
//
//        static void run() throws InterruptedException{
//            System.out.println("\n--- Virtual Thread Comparison ---");
//
//            // Each virtual thread gets its own isolated inherited binding — no copy, no leak
//            Thread t1 = Thread.ofVirtual().name("virtual-1").start(() ->
//                    ScopedValueApproach.handleRequest("tenant-A", "user-101")
//            );
//            Thread t2 = Thread.ofVirtual().name("virtual-2").start(() ->
//                    ScopedValueApproach.handleRequest("tenant-B", "user-202")
//            );
//
//            t1.join();
//            t2.join();
//
//            // tenant-A and tenant-B contexts are fully isolated
//            // even though they may run on the same carrier thread
//        }
//    }

    // =========================================================================
    // MAIN
    // =========================================================================
    public static void main(String[] args) throws InterruptedException {

        System.out.println("=== PART 1: ThreadLocal Approach ===");
        ThreadLocalApproach.handleRequest("tenant-X", "user-001");

        // Uncomment when java 21 preview is used
//        System.out.println("\n=== PART 2: ScopedValue Approach ===");
//        ScopedValueApproach.handleRequest("tenant-X", "user-001");
//
//        System.out.println("\n=== PART 3: ScopedValue with Virtual Threads ===");
//        VirtualThreadComparison.run();

        // KEY DIFFERENCES:
        // ThreadLocal: mutable, manual remove(), dangerous in thread pools & virtual threads
        // ScopedValue: immutable, auto-cleanup, efficient pointer-sharing across virtual threads
    }

}
