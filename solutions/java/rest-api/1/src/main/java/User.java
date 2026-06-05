import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** POJO representing a User in the database. */
public class User {
    private final String name;
    private final Map<String, Double> netDebt;

    private User(String name, Map<String, Double> netDebt) {
        this.name = name;
        this.netDebt = new HashMap<>(netDebt);
    }

    public String name() {
        return name;
    }

    /** IOUs this user owes to other users. */
    public List<Iou> owes() {
        return netDebt.entrySet()
                .stream()
                .filter(entry -> entry.getValue() < 0)
                .map(entry -> new Iou(entry.getKey(), Math.abs(entry.getValue())))
                .toList();
    }

    /** IOUs other users owe to this user. */
    public List<Iou> owedBy() {
        return netDebt.entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> new Iou(entry.getKey(), entry.getValue()))
                .toList();
    }

    private void adjustDebt(String name, double amount) {
        Double debt = this.netDebt.getOrDefault(name, 0.0);
        this.netDebt.put(name, debt + amount);
    }

    public void borrowFrom(String name, double amount) {
        adjustDebt(name, -amount);
    }

    public void lendTo(String name, double amount) {
        adjustDebt(name, amount);
    }

    public double balance() {
        return this.netDebt.values().stream().reduce(0.0, Double::sum);
    }

    public JSONObject toJson() {
        return new JSONObject()
                .put("name", name)
                .put("owes", iousToJSONObject(this.owes()))
                .put("owedBy", iousToJSONObject(this.owedBy()))
                .put("balance", balance());
    }

    private JSONObject iousToJSONObject(List<Iou> ious) {
        JSONObject json = new JSONObject();
        for (Iou iou : ious) {
            json.put(iou.name, iou.amount);
        }
        return json;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String name;
        private final Map<String, Double> netDebt = new HashMap<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder owes(String name, double amount) {
            Double debt = netDebt.getOrDefault(name, 0.0);
            netDebt.put(name, debt - amount);
            return this;
        }

        public Builder owedBy(String name, double amount) {
            Double debt = netDebt.getOrDefault(name, 0.0);
            netDebt.put(name, debt + amount);
            return this;
        }

        public User build() {
            return new User(name, netDebt);
        }
    }
}
