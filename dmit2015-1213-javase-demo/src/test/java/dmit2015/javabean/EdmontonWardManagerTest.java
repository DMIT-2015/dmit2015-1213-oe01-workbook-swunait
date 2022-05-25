package dmit2015.javabean;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EdmontonWardManagerTest {

    static EdmontonWardManager currentWardManager;

    @BeforeAll
    static void beforeAll() {
        currentWardManager = EdmontonWardManager.getInstance();
    }

    @Test
    void count() {
        // There should be 12 records in the dataset
        assertEquals(12, currentWardManager.count());
        currentWardManager.getWards().forEach(ward -> System.out.println(ward.getName1()));
    }

    @Test
    void findWardName1() {
        // latitude=53.64496373183589, longitude=-113.47709291864247
        double latitude = 53.64496373183589;
        double longitude = -113.47709291864247;
        assertEquals("tastawiyiniwak", currentWardManager.findWardName1(latitude, longitude));
    }

    @Test
    void findWardName2() {
        double latitude = 53.64496373183589;
        double longitude = -113.47709291864247;
        assertEquals("tastawiyiniwak Ward", currentWardManager.findWardName2(latitude, longitude));
        latitude=53.47287377003312;
        longitude=-113.35023446875456;
        assertEquals("Sspomitapi Ward", currentWardManager.findWardName2(latitude, longitude));
    }

    @Test
    @DisplayName("Find By Councillor")
    void findByCouncillor() {
        Optional<EdmontonWard> optionalEdmontonWard = currentWardManager.findByCouncillor("Keren Tang");
        assertTrue(optionalEdmontonWard.isPresent());
        EdmontonWard ward = optionalEdmontonWard.get();
        assertEquals("Karhiio", ward.getName1());
        assertEquals("Karhiio Ward", ward.getName2());
    }

    @Test
    @DisplayName("Find Ward By Councillor")
    void findWardByCouncillor() {
        assertEquals("sipiwiyiniwak", currentWardManager.findName1ByCouncillor("Sarah Hamilton"));
    }


}