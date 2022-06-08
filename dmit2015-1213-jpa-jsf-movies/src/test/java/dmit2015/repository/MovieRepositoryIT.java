package dmit2015.repository;

import common.config.ApplicationConfig;


import common.listener.MovieApplicationStartupListener;
import dmit2015.entity.Movie;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.arquillian.transaction.api.annotation.TransactionMode;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;

import jakarta.inject.Inject;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ExtendWith(ArquillianExtension.class)                  // Run with JUnit 5 instead of JUnit 4
public class MovieRepositoryIT {

    @Inject
    private MovieRepository _movieRepository;

    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addAsLibraries(pomFile.resolve("com.h2database:h2:2.1.212").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("org.hsqldb:hsqldb:2.6.1").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("com.microsoft.sqlserver:mssql-jdbc:10.2.1.jre17").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("com.oracle.database.jdbc:ojdbc11:21.5.0.0").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("org.postgresql:postgresql:42.3.1").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("mysql:mysql-connector-java:8.0.28").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("org.mariadb.jdbc:mariadb-java-client:2.7.3").withTransitivity().asFile())
                // .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-spatial:5.6.9.Final").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.hamcrest:hamcrest:2.2").withTransitivity().asFile())
//                .addAsLibraries(pomFile.resolve("org.hibernate:hibernate-core-jakarta:5.6.5.Final").withTransitivity().asFile())
                .addClass(ApplicationConfig.class)
                .addClasses(MovieApplicationStartupListener.class)
                .addClasses(Movie.class, MovieRepository.class)
                .addAsResource("META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE,"beans.xml");
    }

    @Transactional(TransactionMode.ROLLBACK)
    @Order(3)
    @Test
    void shouldCreate() {
        // Arrange
        Movie newMovie = new Movie();
        newMovie.setGenre("Horror");
        newMovie.setPrice(BigDecimal.valueOf(19.99));
        newMovie.setRating("NC-17");
        newMovie.setTitle("The Return of the Java Master");
        newMovie.setReleaseDate(LocalDate.parse("2021-01-21"));
        // Act
        _movieRepository.add(newMovie);
        // Assert
        Optional<Movie> optionalMovie = _movieRepository.findOptionalById(newMovie.getId());
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals(newMovie.getTitle(), existingMovie.getTitle());
        assertEquals(newMovie.getGenre(), existingMovie.getGenre());
        assertEquals(newMovie.getPrice(), existingMovie.getPrice());
        assertEquals(newMovie.getRating(), existingMovie.getRating());
        assertEquals(newMovie.getReleaseDate(), existingMovie.getReleaseDate());

        long createdDateTimeDifference = newMovie.getCreatedDateTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        assertEquals(0, createdDateTimeDifference);

        assertNull(newMovie.getUpdatedDateTime());
    }

    @Order(2)
    @Test
    void shouldFindOne() {
        // Arrange
        final Long editId = 3L;  // for Ghostbusters 2
        // Act
        Optional<Movie> optionalMovie = _movieRepository.findOptionalById(editId);
        // Assert
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        assertEquals("Comedy", existingMovie.getGenre());
        assertEquals(9.99, existingMovie.getPrice().doubleValue());
        assertEquals("PG", existingMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        assertEquals(LocalDate.parse("1986-2-23", formatter).toString(), existingMovie.getReleaseDate().toString());
        assertNotNull(existingMovie.getCreatedDateTime());
    }

    @Order(1)
    @Test
    void shouldFindAll() {
        // Arrange and Act
        List<Movie> queryResultList = _movieRepository.findAll();
        // Assert
        assertEquals(4, queryResultList.size());

        Movie firstMovie = queryResultList.get(0);
        assertEquals("When Harry Met Sally", firstMovie.getTitle());
        assertEquals("Romantic Comedy", firstMovie.getGenre());
        assertEquals(7.99, firstMovie.getPrice().doubleValue());
        assertEquals("G", firstMovie.getRating());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        assertEquals(LocalDate.parse("1989-02-12", formatter).toString(), firstMovie.getReleaseDate().toString());

        Movie lastMovie = queryResultList.get(queryResultList.size() - 1);
        assertEquals("Rio Bravo", lastMovie.getTitle());
        assertEquals("Western", lastMovie.getGenre());
        assertEquals(7.99, lastMovie.getPrice().doubleValue());
        assertEquals("PG-13", lastMovie.getRating());
        assertEquals(LocalDate.parse("1959-04-15", formatter).toString(), lastMovie.getReleaseDate().toString());
    }

    @Order(4)
    @Test
    void shouldUpdate()  {
        // Arrange - Create a new Movie then update the same Movie
        Movie newMovie = new Movie();
        newMovie.setGenre("Adventure");
        newMovie.setPrice(BigDecimal.valueOf(29.99));
        newMovie.setRating("PG");
        newMovie.setTitle("JDK 17 Release Party");
        newMovie.setReleaseDate(LocalDate.parse("2021-09-14"));
        _movieRepository.add(newMovie);
        final Long editId = newMovie.getId();
        Optional<Movie> optionalMovie = _movieRepository.findOptionalById(editId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        // Act - change the genre, title, rating, price, and release date
        existingMovie.setGenre("Action");
        existingMovie.setTitle("JDK 18 Release Party");
        existingMovie.setRating("PG-13");
        existingMovie.setPrice(BigDecimal.valueOf(19.99));
        existingMovie.setReleaseDate(LocalDate.parse("2022-03-22"));
        _movieRepository.update(existingMovie);

        // Assert
        Optional<Movie> optionalUpdatedMovie = _movieRepository.findOptionalById(existingMovie.getId());
        assertTrue(optionalUpdatedMovie.isPresent());
        Movie updatedMovie = optionalUpdatedMovie.get();
        System.out.println("Updated movie: "  + updatedMovie);
        assertNotNull(updatedMovie);
        assertEquals(existingMovie.getTitle(), updatedMovie.getTitle());
        assertEquals(existingMovie.getGenre(), updatedMovie.getGenre());
        assertEquals(existingMovie.getPrice().doubleValue(), updatedMovie.getPrice().doubleValue());
        assertEquals(existingMovie.getRating(), updatedMovie.getRating());
        assertEquals(existingMovie.getReleaseDate(), updatedMovie.getReleaseDate());

        assertNotNull(updatedMovie.getUpdatedDateTime());
        long updatedDateTimeDifference = updatedMovie.getUpdatedDateTime().until(LocalDateTime.now(), ChronoUnit.MINUTES);
        assertEquals(0, updatedDateTimeDifference);

    }

    @Order(5)
    @Test
    void shouldDelete() {
        // Arrange
        final Long editId = 3L;  // for Ghostbusters 2
        Optional<Movie> optionalMovie = _movieRepository.findOptionalById(editId);
        assertTrue(optionalMovie.isPresent());
        Movie existingMovie = optionalMovie.get();
        assertNotNull(existingMovie);
        // Act
        _movieRepository.delete(existingMovie);
        optionalMovie = _movieRepository.findOptionalById(editId);
        // Assert
        assertTrue(optionalMovie.isEmpty());
    }
}