import java.util.*;
import java.util.stream.Collectors;

public class Tournament {
    private List<Team> teams;

    public Tournament() {
        teams = new ArrayList<>();
    }

    public String printTable() {
        final String header = generateHeader();
        final String body = generateBody();

        return header + body;
    }

    private String generateHeader() {
        return "Team                           | MP |  W |  D |  L |  P\n";
    }

    private String generateBody() {
        Collections.sort(teams);
        return teams.stream()
                .map(this::generateBodyLine)
                .collect(Collectors.joining(""));
    }

    private String generateBodyLine(Team team) {
        final StringBuilder builder = new StringBuilder();
        builder.append(String.format("%-31s|", team.name))
                .append(String.format("%3d |", team.getPlayed()))
                .append(String.format("%3d |", team.getWon()))
                .append(String.format("%3d |", team.getDrawn()))
                .append(String.format("%3d |", team.getLost()))
                .append(String.format("%3d\n", team.getPoints()));

        return builder.toString();
    }

    public void applyResults(String matches) {
        Arrays.stream(matches.split("\\n"))
                .forEach(this::playMatch);
    }

    private void playMatch(String result) {
        final String[] parts = result.split(";");
        final Team team1 = findTeam(parts[0]);
        final Team team2 = findTeam(parts[1]);

        if("win".equals(parts[2])) {
            team1.win();
            team2.lost();
        } else if("draw".equals(parts[2])) {
            team1.draw();
            team2.draw();
        } else if("loss".equals(parts[2])) {
            team1.lost();
            team2.win();
        } else{
            throw new IllegalArgumentException("Invalid match result");
        }
    }

    private Team findTeam(String name) {
        Team team = findExistingTeam(name);
        if(team == null) {
            team = addTeam(name);
        }

        return team;
    }

    private Team findExistingTeam(String name) {
        for (Team team : teams) {
            if(team.name.equals(name)) {
                return team;
            }
        }
        return null;
    }

    private Team addTeam(String name) {
        final Team team = new Team(name);
        teams.add(team);
        return team;
    }
}
