package dmit2015.repository;

import common.config.ApplicationConfig;
import common.jpa.AbstractJpaRepository;
import dmit2015.entity.Category;
import dmit2015.entity.Product;
import dmit2015.entity.Supplier;
import jakarta.inject.Inject;
import jakarta.json.JsonObject;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)
public class ProductArquillianIT { // The class must be declared as public

    static String mavenArtifactIdId;

    @Deployment
    public static WebArchive createDeployment() throws IOException, XmlPullParserException {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        mavenArtifactIdId = model.getArtifactId();
        final String archiveName = model.getArtifactId() + ".war";
        return ShrinkWrap.create(WebArchive.class, archiveName)
                .addAsLibraries(pomFile.resolve("org.codehaus.plexus:plexus-utils:3.4.2").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.h2database:h2:2.1.212").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:10.2.1.jre17").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.5.0.0").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.postgresql:postgresql:42.3.1").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("mysql:mysql-connector-java:8.0.28").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.mariadb.jdbc:mariadb-java-client:2.7.3").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core-jakarta:5.6.9.Final").withTransitivity().asFile())
//                 .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-spatial:5.6.9.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(AbstractJpaRepository.class)
                .addPackage("dmit2015.entity")
//                .addClasses(ProductRepository.class)
                .addPackage("dmit2015.repository")
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Inject
    private ProductRepository _productRepository;

    @Inject
    private CategoryRepository _categoryRepository;

    @Inject
    private SupplierRepository _supplierRepository;

    @BeforeAll
    static void beforeAll() {
        // code to execute before test methods are executed
    }

    @BeforeEach
    void beforeEach() {
        // Code to execute before each method such as creating the test data
    }

    @AfterEach
    void afterEach() {
        // code to execute after each test method such as deleting the test data
    }


//    @Order(1)
    @Test
    void shouldFindAll() {
        assertNotNull(_productRepository);
        // Arrange
        List<Product> productList = _productRepository.findAll();
        // Act
        // Assert
        assertEquals(86, productList.size());
    }

    @Test
    void shouldFindByCategoryID() {
        // Arrange and Act
        List<Product> productList = _productRepository.findByCategoryId(1);
        // Assert
        assertEquals(16, productList.size());
    }

//    @Order(2)
    @Test
    void shouldCreate() {
        // Arrange
        Product newProduct = new Product();
        newProduct.setProductName("Product 1");
        Category productCategory = _categoryRepository.findById(1);
        Supplier productSupplier = _supplierRepository.findById(1);
        newProduct.setCategory(productCategory);
        newProduct.setSupplier(productSupplier);
        newProduct.setDiscontinued(false);
        newProduct.setQuantityPerUnit("12");
        newProduct.setUnitPrice(BigDecimal.valueOf(19.99));
        newProduct.setUnitsOnOrder(0);
        // Act
        _productRepository.add(newProduct);
        // Assert
        Product existingProduct = _productRepository.findById(newProduct.getId());
        assertNotNull(existingProduct);


    }
//
//    @Order(3)
//    @Test
//    void shouldFineOne() {
//        // Arrange and Act
//        Product existingProduct = _productRepository.findById(editKey);
//
//        // Assert
//        assertNotNull(existingProduct);
//        // assertEquals("Expected Property Value", existingProduct.getPropertyNam());
//
//    }
//
//    @Order(4)
//    @Test
//    void shouldUpdate() {
//        // Arrange
//        Product existingProduct = _productRepository.findById(editKey);
//        assertNotNull(existingProduct);
//        // Act
//        // existingProduct.setPropertyName("Property Value");
//
//        Product updatedProduct = _productRepository.update(editKey, existingProduct);
//        // Assert
//        // assertEquals(existingProduct.getPropertyName(), updatedProduct.getPropertyName());
//
//    }
//
//    @Order(5)
//    @Test
//    void shouldDelete() {
//        // Arrange and Act
//        _productRepository.delete(editKey);
//        // Assert
//        assertNull(_productRepository.findById(editKey));
//    }
}