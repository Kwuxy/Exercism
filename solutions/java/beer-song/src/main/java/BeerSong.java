class BeerSong {
    public String sing(int bottlesNumber, int beerToDrink) {
        final StringBuilder builder = new StringBuilder();
        while(beerToDrink > 0) {
            builder.append(getPhrase(bottlesNumber));
            beerToDrink--;
            bottlesNumber--;
        }
        return builder.toString();
    }

    private String getPhrase(int bottlesNumber) {
        switch(bottlesNumber) {
            case 0: return "No more bottles of beer on the wall, no more bottles of beer.\n" +
                                    "Go to the store and buy some more, 99 bottles of beer on the wall.\n\n";
            case 1: return "1 bottle of beer on the wall, 1 bottle of beer.\n" +
                                    "Take it down and pass it around, no more bottles of beer on the wall.\n\n";
            case 2: return "2 bottles of beer on the wall, 2 bottles of beer.\n" +
                                    "Take one down and pass it around, 1 bottle of beer on the wall.\n\n";
            default:
                String pluralPhrase = "%d bottles of beer on the wall, %d bottles of beer.\n" +
                        "Take one down and pass it around, %d bottles of beer on the wall.\n\n";
                return String.format(pluralPhrase, bottlesNumber, bottlesNumber, bottlesNumber - 1);
        }
    }

    public String singSong() {
        return sing(99, 100);
    }
}