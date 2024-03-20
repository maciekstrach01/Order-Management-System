public enum Country {
    POLAND ("Poland", "Polska"),
    GERMANY ("Germany", "Niemcy"),
    CZECH_REPUBLIC ("Czech Republic", "Czechy"),
    SLOVAKIA ("Slovakia", "Słowacja"),
    UKRAINE ("Ukraine", "Ukraina"),
    BELARUS ("Belarus", "Białoruś"),
    LITHUANIA ("Lithuania", "Litwa"),
    RUSSIA ("Russia", "Rosja");

    private final String countryName;
    private final String polishCountryName;

    Country(String countryName, String polishCountryName) {
        this.countryName = countryName;
        this.polishCountryName = polishCountryName;
    }

    public String getCountryName() {
        return this.countryName;
    }

    public String getPolishCountryName() {
        return this.polishCountryName;
    }

    @Override
    public String toString() {
        return this.getCountryName();
    }

    public static Country getCountryFromPolishName(String polishName) {
        for (Country country : Country.values())
            if (country.getPolishCountryName().equalsIgnoreCase(polishName)) {
                return country;
            }

        throw new IllegalArgumentException();
    }
}
