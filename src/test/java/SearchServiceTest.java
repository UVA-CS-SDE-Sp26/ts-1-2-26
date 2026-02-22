import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock
    private DatabaseManager mockDatabaseManager;

    private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchService = new SearchService(mockDatabaseManager);
    }

    // TEST 1: search() returns matching missions
    @Test
    void testSearch_returnsMatches() {
        List<MissionRecord> missions = Arrays.asList(
                new MissionRecord(1, "Op Alpha", "Retrieve the stolen documents from Berlin.", "2024-01-01"),
                new MissionRecord(2, "Op Beta", "Surveil the target at the docks.", "2024-01-02"),
                new MissionRecord(3, "Op Gamma", "Recover stolen intel from the embassy.", "2024-01-03")
        );
        when(mockDatabaseManager.getAllMissions()).thenReturn(missions);

        List<MissionRecord> results = searchService.search("stolen");

        assertEquals(2, results.size());
        verify(mockDatabaseManager).getAllMissions();
    }

    // TEST 2: search() is case-insensitive
    @Test
    void testSearch_caseInsensitive() {
        List<MissionRecord> missions = Arrays.asList(
                new MissionRecord(1, "Op Alpha", "Retrieve the STOLEN documents.", "2024-01-01"),
                new MissionRecord(2, "Op Beta", "Surveil the target at the docks.", "2024-01-02")
        );
        when(mockDatabaseManager.getAllMissions()).thenReturn(missions);

        List<MissionRecord> results = searchService.search("stolen");

        assertEquals(1, results.size());
        assertEquals("Op Alpha", results.get(0).getTitle());
    }

    // TEST 3: search() returns empty list when no matches found
    @Test
    void testSearch_noMatches() {
        List<MissionRecord> missions = Arrays.asList(
                new MissionRecord(1, "Op Alpha", "Retrieve the documents from Berlin.", "2024-01-01"),
                new MissionRecord(2, "Op Beta", "Surveil the target at the docks.", "2024-01-02")
        );
        when(mockDatabaseManager.getAllMissions()).thenReturn(missions);

        List<MissionRecord> results = searchService.search("nuclear");

        assertTrue(results.isEmpty());
    }

    // TEST 4: search() returns empty list when database is empty
    @Test
    void testSearch_emptyDatabase() {
        when(mockDatabaseManager.getAllMissions()).thenReturn(new ArrayList<>());

        List<MissionRecord> results = searchService.search("anything");

        assertTrue(results.isEmpty());
        verify(mockDatabaseManager).getAllMissions();
    }

    // TEST 5: search() matches partial words/phrases
    @Test
    void testSearch_partialPhraseMatch() {
        List<MissionRecord> missions = Arrays.asList(
                new MissionRecord(1, "Op Alpha", "Infiltrate the counterintelligence unit.", "2024-01-01"),
                new MissionRecord(2, "Op Beta", "Deliver intelligence report to HQ.", "2024-01-02")
        );
        when(mockDatabaseManager.getAllMissions()).thenReturn(missions);

        List<MissionRecord> results = searchService.search("intel");

        assertEquals(2, results.size());
    }

    // TEST 6: search() with multi-word phrase
    @Test
    void testSearch_multiWordPhrase() {
        List<MissionRecord> missions = Arrays.asList(
                new MissionRecord(1, "Op Alpha", "Secure the safe house in Prague.", "2024-01-01"),
                new MissionRecord(2, "Op Beta", "Surveil the target at the docks.", "2024-01-02")
        );
        when(mockDatabaseManager.getAllMissions()).thenReturn(missions);

        List<MissionRecord> results = searchService.search("safe house");

        assertEquals(1, results.size());
        assertEquals("Op Alpha", results.get(0).getTitle());
    }

    // TEST 7: search() only searches briefs, not titles
    @Test
    void testSearch_doesNotSearchTitle() {
        List<MissionRecord> missions = Arrays.asList(
                new MissionRecord(1, "Operation Nightfall", "Routine patrol of the eastern border.", "2024-01-01")
        );
        when(mockDatabaseManager.getAllMissions()).thenReturn(missions);

        List<MissionRecord> results = searchService.search("Nightfall");

        assertTrue(results.isEmpty());
    }

    // TEST 8: search() calls getAllMissions() exactly once
    @Test
    void testSearch_callsDatabaseOnce() {
        when(mockDatabaseManager.getAllMissions()).thenReturn(new ArrayList<>());

        searchService.search("test");

        verify(mockDatabaseManager, times(1)).getAllMissions();
    }
}