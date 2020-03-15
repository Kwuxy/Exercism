class Team implements Comparable<Team> {
    String name;
    private int played;
    private int won;
    private int drawn;
    private int lost;
    private int points;

    Team(String name) {
        this.name = name;
        played = 0;
        won = 0;
        drawn = 0;
        lost = 0;
        points = 0;
    }

    void win() {
        played++;
        won++;
        points += 3;
    }

    void draw() {
        played++;
        drawn++;
        points += 1;
    }

    void lost() {
        played++;
        lost++;
    }

    public int getPlayed() {
        return played;
    }

    public int getWon() {
        return won;
    }

    public int getDrawn() {
        return drawn;
    }

    public int getLost() {
        return lost;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(Team o) {
        final int compareOnPoints = Integer.compare(o.getPoints(), this.getPoints());
        return compareOnPoints == 0 ? this.name.compareTo(o.name) : compareOnPoints;
    }
}
