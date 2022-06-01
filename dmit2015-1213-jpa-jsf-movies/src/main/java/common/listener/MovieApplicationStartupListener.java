package common.listener;

import dmit2015.entity.Movie;
import dmit2015.repository.MovieRepository;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.logging.Logger;

@WebListener
public class MovieApplicationStartupListener implements ServletContextListener {

    private Logger _logger = Logger.getLogger(MovieApplicationStartupListener.class.getName());

    @Inject
    MovieRepository _movieRepository;

    public MovieApplicationStartupListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        /* This method is called when the servlet context is initialized(when the Web application is deployed). */

        _movieRepository.deleteAll();

        try {
            Movie movie1 = new Movie();
//            movie1.setUsername("DAUSTIN");
            movie1.setTitle("When Harry Met Sally");
            movie1.setReleaseDate(LocalDate.parse("1989-02-12"));
            movie1.setGenre("Romantic Comedy");
            movie1.setPrice(BigDecimal.valueOf(7.99));
            movie1.setRating("G");
            _movieRepository.add(movie1);

            Movie movie2 = new Movie();
//            movie2.setUsername("DAUSTIN");
            movie2.setTitle("Ghostbusters");
            movie2.setReleaseDate(LocalDate.parse("1984-03-13"));
            movie2.setGenre("Comedy");
            movie2.setPrice(BigDecimal.valueOf(8.99));
            movie2.setRating("PG");
            _movieRepository.add(movie2);

            Movie movie3 = new Movie();
//            movie3.setUsername("DAUSTIN");
            movie3.setTitle("Ghostbusters 2");
            movie3.setReleaseDate(LocalDate.parse("1986-02-23"));
            movie3.setGenre("Comedy");
            movie3.setPrice(BigDecimal.valueOf(9.99));
            movie3.setRating("PG");
            _movieRepository.add(movie3);

            Movie movie4 = new Movie();
//            movie4.setUsername("DAUSTIN");
            movie4.setTitle("Rio Bravo");
            movie4.setReleaseDate(LocalDate.parse("1959-04-15"));
            movie4.setGenre("Western");
            movie4.setPrice(BigDecimal.valueOf(7.99));
            movie4.setRating("PG-13");
            _movieRepository.add(movie4);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}