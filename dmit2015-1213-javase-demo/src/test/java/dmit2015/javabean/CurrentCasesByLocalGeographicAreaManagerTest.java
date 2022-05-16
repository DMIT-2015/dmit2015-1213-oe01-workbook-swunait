package dmit2015.javabean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CurrentCasesByLocalGeographicAreaManagerTest {

    static CurrentCasesByLocalGeographicAreaManager currentCasesManager;

    @BeforeAll
    static void beforeAll() {
        currentCasesManager = CurrentCasesByLocalGeographicAreaManager.getInstance();
    }

    @Test
    void count() {
        // There should be 132 records in the dataset
        assertEquals(132, currentCasesManager.count());
    }

    @Test
    void findAll() {
        List<CurrentCasesByLocalGeographicArea> page1Data = currentCasesManager.findAll(1, 15);
        // There should be 15 records returned
        assertEquals(15, page1Data.size());
        // The first record location should contain "Eastwood"
        assertTrue(page1Data.get(0).getLocation().contains("Eastwood"));
        // The last record location should contain "West Jasper Place"
        assertTrue(page1Data.get(page1Data.size() - 1).getLocation().contains("West Jasper Place"));
    }

    @Test
    void findByCity() {
        // There should be 15 locations in Edmonton
        List<CurrentCasesByLocalGeographicArea> edmontonData = currentCasesManager.findByCity("Edmonton");
        assertEquals(15, edmontonData.size());
    }

    @Test
    void totalPopulationCaseDataByCity() {
        // The total populate case data in Edmonton should be 1_037_455
        assertEquals(1_037_455, currentCasesManager.totalPopulationCaseDataByCity("Edmonton"));
        // The total populate case data in Calgary should be 1_367_007
        assertEquals(1_367_007, currentCasesManager.totalPopulationCaseDataByCity("Calgary"));
    }

    @Test
    void populationByCityMap() {
        // There should be 15 locations in Edmonton
        Map<String, Integer> edmontonMap = currentCasesManager.locationPopulationMapByCity("Edmonton");
        assertEquals(15, edmontonMap.size());
        edmontonMap.entrySet().forEach(item -> System.out.println(item.getKey() + ":" + item.getValue()));
        // The first record should have key of "Rutherford (& Nearby Neighbourhoods)" and value of 112_256
        assertEquals(112_256, edmontonMap.get("Rutherford (& Nearby Neighbourhoods)"));
        // The last record should have key of "Abbottsfield (& Nearby Neighbourhoods)" and value of 14_582
        assertEquals(14_582, edmontonMap.get("Abbottsfield (& Nearby Neighbourhoods)"));
    }
}