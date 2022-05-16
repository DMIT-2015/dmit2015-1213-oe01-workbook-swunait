package dmit2015.javabean;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class EnforcementZoneCentreManagerTest {

    static EnforcementZoneCentreManager currentEnforcementZoneCentreManager;
    @BeforeAll
    static void beforeAll() {
        currentEnforcementZoneCentreManager = EnforcementZoneCentreManager.getInstance();
    }

    @Test
    void count() {
        // There should be exactly 209 records
        assertEquals(209, currentEnforcementZoneCentreManager.count());
    }

    @Test
    void findAll() {
        List<EnforcementZoneCentre> queryResultList = currentEnforcementZoneCentreManager.findAll(1, 14);
        // There should be exactly 14 results returned
        assertEquals(14, queryResultList.size());
        // SiteID of the first result should be 7117
        assertEquals(7117, queryResultList.get(0).getSiteId());
        // SiteID of the last result should be 2756
        assertEquals(2756, queryResultList.get(queryResultList.size() - 1).getSiteId());

    }

    @Test
    void findByReason() {
        // There should be 39 results with "History of Collisions" for the reason
        List<EnforcementZoneCentre> queryResultList1 = currentEnforcementZoneCentreManager.findByReason("History of Collisions");
        assertEquals(39, queryResultList1.size());

        // There should be 50 results with ""History of Speeding" for the reason
        List<EnforcementZoneCentre> queryResultList2 = currentEnforcementZoneCentreManager.findByReason("History of Speeding");
        assertEquals(50, queryResultList2.size());
    }

    @Test
    void findBySpeedLimit() {
        // There should be 96 results with a speed limit of 30
        assertEquals(96, currentEnforcementZoneCentreManager.findBySpeedLimit(30).size());
    }

    @Test
    void findBySiteID() {
        // There should be result with a SiteID of 7117
        Optional<EnforcementZoneCentre> optionalEnforcementZoneCentre = currentEnforcementZoneCentreManager.findBySiteID((short) 7117);
        assertTrue(optionalEnforcementZoneCentre.isPresent());
        // Get the value from the Optional container
        EnforcementZoneCentre currentEnforcementZoneCentre = optionalEnforcementZoneCentre.get();
        // The value from the Optional container should not be null
        assertNotNull(currentEnforcementZoneCentre);
        // The Location Description should be "170 St between 109 - 110 Ave,60,History of Speeding"
        assertEquals("170 St between 109 - 110 Ave", currentEnforcementZoneCentre.getLocationDescription());
        // The Reason Code should contain "History of Speeding"
        assertTrue(currentEnforcementZoneCentre.getReasonCodes().contains("History of Speeding"));
        // The Reason Code should contain "History of Collisions"
        assertTrue(currentEnforcementZoneCentre.getReasonCodes().contains("History of Collisions"));
        // The SpeedLimit should be 60
        assertEquals(60, currentEnforcementZoneCentre.getSpeedLimit());
    }

    @Test
    void findDistinctSpeedLimits() {
        // There should be 7 distinct speed limits
        List<Integer> speedLimits = currentEnforcementZoneCentreManager.findDistinctSpeedLimits();
        assertEquals(7, speedLimits.size());
        // The first speed limit should be 30
        assertEquals(30, speedLimits.get(0));
        // The last speed limit should be 100
        assertEquals(100, speedLimits.get(speedLimits.size() - 1));
    }

    @Test
    void highestSpeedLimit() {
        // The highest SpeedLimit should be 100
        assertEquals(100, currentEnforcementZoneCentreManager.highestSpeedLimit());
    }

    @Test
    void lowestSpeedLimit() {
        // The lowest SpeedLimit should be 30
        assertEquals(30, currentEnforcementZoneCentreManager.lowestSpeedLimit());
    }

    @Test
    void averageSpeedLimit() {
        // The lowest SpeedLimit should be 47.7
        assertEquals(47.7, currentEnforcementZoneCentreManager.averageSpeedLimit(), 0.1);
    }
}