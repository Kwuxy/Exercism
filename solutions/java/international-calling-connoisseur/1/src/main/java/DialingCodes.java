import java.util.HashMap;
import java.util.Map;

public class DialingCodes {

    private final Map<Integer, String> countryCodes = new HashMap<>();

    public Map<Integer, String> getCodes() {
        return countryCodes;
    }

    public void setDialingCode(Integer code, String country) {
        countryCodes.put(code, country);
    }

    public String getCountry(Integer code) {
        return countryCodes.get(code);
    }

    public void addNewDialingCode(Integer code, String country) {
        if (countryCodes.containsKey(code))
            return;

        if (countryCodes.containsValue(country))
            return;

        setDialingCode(code, country);
    }

    public Integer findDialingCode(String country) {
        for (Map.Entry<Integer, String> entry : countryCodes.entrySet()) {
            if (entry.getValue().equals(country)) {
                return entry.getKey();
            }
        }

        return null;
    }

    public void updateCountryDialingCode(Integer code, String country) {
        Integer currentCode = findDialingCode(country);
        if (currentCode == null)
            return;

        countryCodes.remove(currentCode);
        addNewDialingCode(code, country);
    }
}
