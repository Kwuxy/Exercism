class RaindropConverter {

    String convert(int number) {
        StringBuilder builder = new StringBuilder();
        if (number % 3 == 0) {
            builder.append("Pling");
        }

        if (number % 5 == 0) {
            builder.append("Plang");
        }

        if (number % 7 == 0) {
            builder.append("Plong");
        }

        String result = builder.toString();
        if (!"".equals(result)) {
            return result;
        }

        return Integer.toString(number);
    }
}
