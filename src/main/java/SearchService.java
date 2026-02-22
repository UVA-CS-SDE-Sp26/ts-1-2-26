import java.util.List;
import java.util.ArrayList;
public class SearchService {
    private DatabaseManager databaseManager;
    public SearchService(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }
    public List<MissionRecord> search(String phrase) {
        List<MissionRecord> allMissions = databaseManager.getAllMissions();
        List<MissionRecord> matches = new ArrayList<>();

        String lowerPhrase = phrase.toLowerCase();

        for (MissionRecord mission : allMissions) {
            if (mission.getBrief().toLowerCase().contains(lowerPhrase)) {
                matches.add(mission);
            }
        }

        return matches;
    }
}
