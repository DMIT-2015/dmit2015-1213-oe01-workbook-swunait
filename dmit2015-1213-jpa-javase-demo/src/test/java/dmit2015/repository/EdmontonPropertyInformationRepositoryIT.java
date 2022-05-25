package dmit2015.repository;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EdmontonPropertyInformationRepositoryIT {

    @Test
    void count() {
        // Arrange
        EdmontonPropertyInformationRepository propertyInformationRepository = new EdmontonPropertyInformationRepository();
        // Act
        long recordCount = propertyInformationRepository.count();
        // Assert
        assertEquals(406_209, recordCount);
    }

    @Test
    void lowestYear() {
        // Arrange
        EdmontonPropertyInformationRepository propertyInformationRepository = new EdmontonPropertyInformationRepository();
        // Act
        int year = propertyInformationRepository.lowestYear();
        // Assert
        assertEquals(1881, propertyInformationRepository.lowestYear());
    }
}