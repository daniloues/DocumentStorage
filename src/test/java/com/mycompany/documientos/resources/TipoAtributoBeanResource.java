//package com.mycompany.documientos.resources;
//
///**
// *
// * @author daniloues
// */
//import java.sql.Connection;
//import java.nio.file.Paths;
//import java.sql.DriverManager;
//import org.junit.jupiter.api.Assertions;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.Order;
//import org.testcontainers.containers.GenericContainer;
//import org.testcontainers.containers.Network;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.containers.wait.strategy.Wait;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.MountableFile;
//
//@Testcontainers
//public class TipoAtributoBeanResource {
//
//    static Network network = Network.newNetwork();
//
//    private static final MountableFile war = MountableFile.forHostPath(Paths.get("target/Documientos-1.0-SNAPSHOT.war").toAbsolutePath(), 0777);
//
//    @Container
//    GenericContainer payara = new GenericContainer("payara/server-full:6.2024.3-jdk17")
//            .withCopyFileToContainer(war, "/opt/payara/deployments/aplicacion.war")
//            .waitingFor(Wait.forLogMessage(".*deploy AdminCommandApplication deployed with name aplicacion.*", 1))
//            .withExposedPorts(8080)
//            .withNetwork(network)
//            .dependsOn(pgdb)
//            .withEnv("DB_USER", pgdb.getDatabaseName())
//            .withEnv("DB_PASSWORD", pgdb.getPassword())
//            .withEnv("DB_JDBC_URL", "jdbc:postgresql://" + pgdb.getContainerIpAddress() + ":" + pgdb.getMappedPort(5432) + "/" + pgdb.getDatabaseName());
//
//    @Container
//    static PostgreSQLContainer pgdb = new PostgreSQLContainer<>("postgres:15.3-alpine")
//            .withDatabaseName("documientostpi135")
//            .withPassword("abc123")
//            .withUsername("postgres")
//            .withInitScript("documientos.sql")
//            .withExposedPorts(5432)
//            .withNetwork(network)
//            .withNetworkAliases("pgdb");
//
//    @Test
//    @Order(1)
//    public void testAtributBeanResource() throws Exception {
//
//        String pghost = "jdbc:postgresql://" + pgdb.getContainerIpAddress() + ":" + pgdb.getMappedPort(5432) + "/" + pgdb.getDatabaseName();
//
//        System.out.println("pingIntegracion");
//        //http://localhost:8080/aplicacion/resources/jakartaee10
//
//        //        fail("The test case is a prototype.");
//        Assertions.assertTrue(payara.isRunning());
//
//        Assertions.assertTrue(pgdb.isRunning());
//
//        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + pgdb.getContainerIpAddress() + ":" + pgdb.getMappedPort(5432) + "/" + pgdb.getDatabaseName(), pgdb.getUsername(), pgdb.getPassword())) {
//            // Execute a simple SQL query to test connectivity
//            if (connection.isValid(5)) {
//                System.out.println(pghost);
//                System.out.println("Es valida");
//                final String logs = payara.getLogs();
//                System.out.println(logs);
//
//            }
//        }
//    }
//
//}
