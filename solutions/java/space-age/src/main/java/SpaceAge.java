class SpaceAge {
    private final double ageOnEarth;

    SpaceAge(double seconds) {
        final int EARTH_ORBITAL_PERIOD_AS_SECONDS = 31557600;
        this.ageOnEarth = seconds / EARTH_ORBITAL_PERIOD_AS_SECONDS;
    }

    double onEarth() {
        return ageOnEarth;
    }

    double onMercury() {
        final double MERCURY_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD = 0.2408467;
        return this.ageOnEarth / MERCURY_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD;
    }

    double onVenus() {
        final double VENUS_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD = 0.6151972;
        return this.ageOnEarth / VENUS_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD;
    }

    double onMars() {
        final double MARS_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD = 1.8808158;
        return this.ageOnEarth / MARS_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD;
    }

    double onJupiter() {
        final double JUPITER_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD = 11.862615;
        return this.ageOnEarth / JUPITER_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD;
    }

    double onSaturn() {
        final double SATURN_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD = 29.447498;
        return this.ageOnEarth / SATURN_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD;
    }

    double onUranus() {
        final double URANUS_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD = 84.016846;
        return this.ageOnEarth / URANUS_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD;
    }

    double onNeptune() {
        final double NEPTUNE_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD = 164.79132;
        return this.ageOnEarth / NEPTUNE_ORBITAL_PERIOD_AS_EARTH_ORBITAL_PERIOD;
    }

}
