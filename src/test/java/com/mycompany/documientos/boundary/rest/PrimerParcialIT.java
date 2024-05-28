package com.mycompany.documientos.boundary.rest;

import com.mycompany.documientos.entity.Atributo;
import com.mycompany.documientos.entity.Documento;
import com.mycompany.documientos.entity.Metadato;
import com.mycompany.documientos.entity.Taxonomia;
import com.mycompany.documientos.entity.TipoAtributo;
import com.mycompany.documientos.entity.TipoDocumento;
import com.mycompany.documientos.resources.RestResourceHeaderPattern;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.Invocation;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;

/**
 *
 * @author jcpenya
 */
@Testcontainers
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PrimerParcialIT {

// Variables de estados para secuencia de pasos en la prueba
    static Integer ID_TIPO_ATRIBUTO_CREADO;
    static Integer ID_TIPO_DOCUMENTO_CREADO;
    static Long ID_ATRIBUTO_CREADO;
    static Long ID_DOCUMENTO_CREADO;
    static Long ID_TAXONOMIA_CREADO;
    static Long ID_METADATO_CREADO;

    static Network network = Network.newNetwork();
    static Integer puertoDB;
    private static final MountableFile war = MountableFile.forHostPath(Paths.get("target/Documientos-1.0-SNAPSHOT.war").toAbsolutePath(), 0777);

    static PostgreSQLContainer pgdb;

    static GenericContainer payara;

    static Client cliente;
    static WebTarget target;

    private static final File postgresConf = new File("src/test/resources/pg_hba.conf");

    @BeforeAll
    public static void setup() throws SQLException, UnsupportedOperationException, IOException, InterruptedException {

        pgdb = new PostgreSQLContainer<>("postgres:15.3-alpine")
                .withDatabaseName("documientostpi135")
                .withPassword("abc123")
                .withUsername("postgres")
                .withInitScript("documientos.sql")
                .withExposedPorts(5432)
                .withNetwork(network)
                .withNetworkAliases("pgdb")
                .withLogConsumer(outputFrame -> System.out.println("[PostgreSQL Container] " + outputFrame.getUtf8String()))
                .waitingFor(Wait.forListeningPort());

        pgdb.start();

        pgdb.withCopyFileToContainer(MountableFile.forHostPath(postgresConf.getAbsolutePath()), "/var/lib/postgresql/data/pg_hba.conf");

        // Define the command to list the contents of the directory
        String[] command = {"cat", "/var/lib/postgresql/data/pg_hba.conf"};

// Execute the command within the container and get the output
        String directoryContents = pgdb.execInContainer(command).getStdout();

// Print or log the contents of the directory
        System.out.println("Contents of /var/lib/postgresql/data:\n" + directoryContents);

        //PROBANDO QUE REALMENTE SE CREA Y CORRE LA BASE DE DATOS, FUNCIONA
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://" + pgdb.getContainerIpAddress() + ":" + pgdb.getMappedPort(5432) + "/" + pgdb.getDatabaseName(), pgdb.getUsername(), pgdb.getPassword())) {
            // Execute a simple SQL query to test connectivity
            Statement stmt = connection.createStatement();

            // Execute a query
            ResultSet rs = stmt.executeQuery("SELECT * FROM tipo_atributo");

            // Process the result set
            while (rs.next()) {
                // Retrieve by column name
                int tipo_atributo_id = rs.getInt("id_tipo_atributo");
                String name = rs.getString("nombre");
                // Print the values or do whatever processing you need
                System.out.println("ID: " + tipo_atributo_id + ", Name: " + name);
            }
        }

        puertoDB = pgdb.getMappedPort(5432);

        // A PESAR QUE LA URL ES CORRECTA, PAYARA ES INCAPAZ DE CONECTARSE
        payara = new GenericContainer<>("payara/full_pg:6.2024.3")
                .waitingFor(Wait.forLogMessage(".*JMXStartupService has started JMXConnector on JMXService.*", 1))
                .withExposedPorts(8080)
                .withLogConsumer(outputFrame -> System.out.println("[Payara Container] " + outputFrame.getUtf8String()))
                .withNetwork(network)
                .dependsOn(pgdb)
                .withCopyFileToContainer(war, "/opt/payara/deployments/aplicacion.war")
                .withEnv("POSTGRES_USER", pgdb.getUsername())
                .withEnv("POSTGRES_PASSWORD", pgdb.getPassword())
                .withEnv("POSTGRES_DBNAME", pgdb.getDatabaseName())
                .withEnv("POSTGRES_PORT", "5432")
                .withEnv("POSTGRES_SERVERNAME", "pgdb");

        payara.start();

        cliente = ClientBuilder.newClient();
        target = cliente.target(
                String.format("http://localhost:%d/aplicacion/resources/",
                        payara.getMappedPort(8080)));

    }

    @Test
    @Order(1)
    public void testCreateTipoAtributo() {
        System.out.println("createIT");
        TipoAtributo nuevo = new TipoAtributo();
        nuevo.setIdTipoAtributo(1);
        Invocation.Builder builder = target.path("tipoatributo").request(MediaType.APPLICATION_JSON);
        Response respuesta = builder.post(Entity.entity(null, MediaType.APPLICATION_JSON));
        // payload nulo

        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        // payload vacio
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO));
        // payload correcto
        nuevo.setIdTipoAtributo(null);
        nuevo.setExpresionRegular("a");
        nuevo.setIndicacionesScreen("cualquier texto");
        nuevo.setNombre("texto plano");
        nuevo.setNombreScreen("Escriba lo que desee");
        nuevo.setObservaciones("ninguna");
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        String[] lex = respuesta.getHeaderString("Location").split("/");
        ID_TIPO_ATRIBUTO_CREADO = Integer.valueOf(lex[lex.length - 1]);
    }

    @Test
    @Order(2)
    public void testCreateTipoDocumento() {
        System.out.println("createTipoDocumentoIT");
        TipoDocumento nuevo = new TipoDocumento();
        nuevo.setIdTipoDocumento(1);
        Invocation.Builder builder = target.path("tipodocumento").request(MediaType.APPLICATION_JSON);
        Response respuesta = builder.post(Entity.entity(null, MediaType.APPLICATION_JSON));
        // payload nulo
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        // payload vacio
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO));
        // payload correcto
        nuevo.setIdTipoDocumento(null);
        nuevo.setActivo(Boolean.TRUE);
        nuevo.setNombre("partida de matrimonio");
        nuevo.setObservaciones("ninguna");
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        String[] lex = respuesta.getHeaderString("Location").split("/");
        ID_TIPO_DOCUMENTO_CREADO = Integer.valueOf(lex[lex.length - 1]);
    }

    @Test
    @Order(3)
    public void testCreateAtributo() {
        System.out.println("createAtributoIT");
        Atributo nuevo = new Atributo();
        nuevo.setIdAtributo(1l);
        Invocation.Builder builder = target.path("tipodocumento/{idTipoDocumento}/atributo")
                .resolveTemplate("idTipoDocumento", ID_TIPO_DOCUMENTO_CREADO)
                .request(MediaType.APPLICATION_JSON);
        Response respuesta = builder.post(Entity.entity(null, MediaType.APPLICATION_JSON));
        // payload nulo
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        // payload vacio
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO));
        // payload correcto
        nuevo.setIdAtributo(null);
        nuevo.setIdTipoAtributo(new TipoAtributo(ID_TIPO_ATRIBUTO_CREADO));
        nuevo.setIdTipoDocumento(new TipoDocumento(ID_TIPO_DOCUMENTO_CREADO));
        nuevo.setNombre("algun nombre para atributo");
        nuevo.setNombrePantalla("algun nombre para atributo");
        nuevo.setObligatorio(Boolean.TRUE);
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        String[] lex = respuesta.getHeaderString("Location").split("/");
        ID_ATRIBUTO_CREADO = Long.valueOf(lex[lex.length - 1]);
    }

    @Test
    @Order(4)
    public void testCreateDocumento() {
        System.out.println("createDocumentoIT");
        Documento nuevo = new Documento();
        nuevo.setIdDocumento(1l);
        Invocation.Builder builder = target.path("documento")
                .request(MediaType.APPLICATION_JSON);
        Response respuesta = builder.post(Entity.entity(null, MediaType.APPLICATION_JSON));
        // payload nulo
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        // payload vacio
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO));
        // payload correcto
        nuevo.setIdDocumento(null);

        nuevo.setCreadoPor("chepe");
        nuevo.setNombre("partida de matrimonio de maria");
        nuevo.setUbicacionFisica("archivero 2, gaveta 5");

        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        String[] lex = respuesta.getHeaderString("Location").split("/");
        ID_DOCUMENTO_CREADO = Long.valueOf(lex[lex.length - 1]);
    }

    @Test
    @Order(5)
    public void testCreateTaxonomia() {
        System.out.println("createTaxonomiaIT");

        Taxonomia nuevo = new Taxonomia();
        nuevo.setIdTaxonomia(1l);
        Invocation.Builder builder = target.path("documento/{idDocumento}/taxonomia")
                .resolveTemplate("idDocumento", ID_DOCUMENTO_CREADO)
                .request(MediaType.APPLICATION_JSON);
        Response respuesta = builder.post(Entity.entity(null, MediaType.APPLICATION_JSON));
        // payload nulo
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        // payload vacio
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO));
        // payload correcto
        nuevo.setIdTaxonomia(null);
        nuevo.setIdDocumento(new Documento(ID_DOCUMENTO_CREADO));
        nuevo.setIdTipoDocumento(new TipoDocumento(ID_TIPO_DOCUMENTO_CREADO));
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        String[] lex = respuesta.getHeaderString("Location").split("/");
        ID_TAXONOMIA_CREADO = Long.valueOf(lex[lex.length - 1]);
    }

    @Test
    @Order(6)
    public void testCreateMetadato() {
        System.out.println("createMetadatoIT");

        Metadato nuevo = new Metadato();
        nuevo.setIdMetadato(1l);
        Invocation.Builder builder = target.path("documento/{idDocumento}/metadato")
                .resolveTemplate("idDocumento", ID_DOCUMENTO_CREADO)
                .request(MediaType.APPLICATION_JSON);
        Response respuesta = builder.post(Entity.entity(null, MediaType.APPLICATION_JSON));
        // payload nulo
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        // payload vacio
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(RestResourceHeaderPattern.STATUS_PARAMETRO_EQUIVOCADO, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey(RestResourceHeaderPattern.DETALLE_PARAMETRO_EQUIVOCADO));

        //// crear tipo de documento equivocado
        Invocation.Builder builderT = target.path("tipodocumento").request(MediaType.APPLICATION_JSON);
        TipoDocumento nuevoT = new TipoDocumento();
        nuevoT.setIdTipoDocumento(null);
        nuevoT.setActivo(Boolean.TRUE);
        nuevoT.setNombre("tipo equivocado");
        nuevoT.setObservaciones("ninguna");
        Response respuestaT = builderT.post(Entity.entity(nuevoT, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuestaT.getStatus());
        Assertions.assertTrue(respuestaT.getHeaders().containsKey("Location"));
        String[] lex = respuestaT.getHeaderString("Location").split("/");
        nuevoT.setIdTipoDocumento(Integer.valueOf(lex[lex.length - 1]));

        //// crear atributo equivocado
        Atributo nuevoA = new Atributo();
        nuevoA.setIdAtributo(1l);
        Invocation.Builder builderA = target.path("tipodocumento/{idTipoDocumento}/atributo")
                .resolveTemplate("idTipoDocumento", nuevoT.getIdTipoDocumento())
                .request(MediaType.APPLICATION_JSON);

        nuevoA.setIdAtributo(null);
        nuevoA.setIdTipoAtributo(new TipoAtributo(ID_TIPO_ATRIBUTO_CREADO));
        nuevoA.setIdTipoDocumento(nuevoT);
        nuevoA.setNombre("atributo equivocado");
        nuevoA.setNombrePantalla("algun nombre para atributo equivocado");
        nuevoA.setObligatorio(Boolean.TRUE);
        respuesta = builderA.post(Entity.entity(nuevoA, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuesta.getStatus());
        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        lex = respuesta.getHeaderString("Location").split("/");
        nuevoA.setIdAtributo(Long.valueOf(lex[lex.length - 1]));

        //// intentar crear metadato que no pertenece a la taxonomia
        nuevo.setIdMetadato(null);
        nuevo.setIdAtributo(nuevoA);
        nuevo.setIdDocumento(new Documento(ID_DOCUMENTO_CREADO));
        nuevo.setValor("algun valor");

        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(405, respuesta.getStatus());
        // crear atributo valido
        nuevo = new Metadato();
        nuevo.setIdAtributo(new Atributo(ID_ATRIBUTO_CREADO));
        nuevo.setIdDocumento(new Documento(ID_DOCUMENTO_CREADO));
        nuevo.setValor("valor valido");
        respuesta = builder.post(Entity.entity(nuevo, MediaType.APPLICATION_JSON));
        Assertions.assertEquals(201, respuesta.getStatus());

        Assertions.assertTrue(respuesta.getHeaders().containsKey("Location"));
        lex = respuesta.getHeaderString("Location").split("/");
        ID_METADATO_CREADO = Long.valueOf(lex[lex.length - 1]);
    }

    @Test
    @Order(7)
    public void testValidarResultados() {
        System.out.println("validarResultadosIT Sera");

        System.out.println("============== ID TIPO ATRIBUTO GENERADO " + ID_TIPO_ATRIBUTO_CREADO);
        Response respuesta = target.path("tipoatributo/{idTipoAtributo}")
                .resolveTemplate("idTipoAtributo", ID_TIPO_ATRIBUTO_CREADO)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(200, respuesta.getStatus());
        String json = respuesta.readEntity(String.class);
        Assertions.assertNotNull(json);
        System.out.println("TipoAtributo " + json);
        // tipo documento
        System.out.println("============== ID TIPO DOCUMENTO GENERADO " + ID_TIPO_DOCUMENTO_CREADO);
        respuesta = target.path("tipodocumento/{idTipoDocumento}")
                .resolveTemplate("idTipoDocumento", ID_TIPO_DOCUMENTO_CREADO)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(200, respuesta.getStatus());
        json = respuesta.readEntity(String.class);
        Assertions.assertNotNull(json);
        System.out.println("TipoDocumento " + json);
        // atributo
        System.out.println("============== ID ATRIBUTO GENERADO " + ID_ATRIBUTO_CREADO);
        respuesta = target.path("tipodocumento/{idTipoDocumento}/atributo/{idAtributo}")
                .resolveTemplate("idTipoDocumento", ID_TIPO_DOCUMENTO_CREADO)
                .resolveTemplate("idAtributo", ID_ATRIBUTO_CREADO)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();
        System.out.print(payara.getLogs());
        Assertions.assertEquals(200, respuesta.getStatus());
        json = respuesta.readEntity(String.class);
        Assertions.assertNotNull(json);
        System.out.println("Atributo " + json);
        // documento
        System.out.println("============== ID DOCUMENTO GENERADO " + ID_DOCUMENTO_CREADO);
        respuesta = target.path("documento/{idDocumento}")
                .resolveTemplate("idDocumento", ID_DOCUMENTO_CREADO)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();

        Assertions.assertEquals(200, respuesta.getStatus());
        json = respuesta.readEntity(String.class);
        Assertions.assertNotNull(json);
        System.out.println("Atributo " + json);
        // taxonomia
        System.out.println("============== ID TAXONOMIA GENERADO " + ID_TAXONOMIA_CREADO);
        respuesta = target.path("documento/{idDocumento}/taxonomia/{idTaxonomia}")
                .resolveTemplate("idDocumento", ID_DOCUMENTO_CREADO)
                .resolveTemplate("idTaxonomia", ID_TAXONOMIA_CREADO)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(200, respuesta.getStatus());
        json = respuesta.readEntity(String.class);
        Assertions.assertNotNull(json);
        System.out.println("Taxonomia " + json);
        // metadato
        System.out.println("============== ID METADATO GENERADO " + ID_METADATO_CREADO);
        respuesta = target.path("documento/{idDocumento}/metadato/{idMetadato}")
                .resolveTemplate("idDocumento", ID_DOCUMENTO_CREADO)
                .resolveTemplate("idMetadato", ID_METADATO_CREADO)
                .request(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .get();
        Assertions.assertEquals(200, respuesta.getStatus());
        json = respuesta.readEntity(String.class);
        Assertions.assertNotNull(json);
        System.out.println("Metadato " + json);

    }

}
