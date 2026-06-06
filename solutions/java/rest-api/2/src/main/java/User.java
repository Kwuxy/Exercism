import org.json.JSONObject;

import java.util.*;

import static java.util.Collections.unmodifiableList;

/** POJO representing a User in the database. */
public class User {
    private final String name;
    private final List<Iou> owes;
    private final List<Iou> owedBy;

    private User(String name, List<Iou> owes, List<Iou> owedBy) {
        this.name = name;
        this.owes = new ArrayList<>(owes);
        this.owedBy = new ArrayList<>(owedBy);
    }

    public String name() {
        return name;
    }

    /** IOUs this user owes to other users. */
    public List<Iou> owes() {
        return unmodifiableList(owes);
    }

    /** IOUs other users owe to this user. */
    public List<Iou> owedBy() {
        return unmodifiableList(owedBy);
    }

    private double popNetDebt(String name) {
        if (this.owes.stream().anyMatch(iou -> iou.name.equals(name))) {
            double debt = this.owes.stream().filter(iou -> iou.name.equals(name)).mapToDouble(iou -> iou.amount).sum();
            this.owes.removeIf(iou -> iou.name.equals(name));
            return -debt;
        }

        if (this.owedBy.stream().anyMatch(iou -> iou.name.equals(name))) {
            double debt = this.owedBy.stream().filter(iou -> iou.name.equals(name)).mapToDouble(iou -> iou.amount).sum();
            this.owedBy.removeIf(iou -> iou.name.equals(name));
            return debt;
        }

        return 0.0;
    }

    public void borrowFrom(String name, double amount) {
        double debt = amount - popNetDebt(name);
        if (debt == 0) return;
        if (debt < 0) {
            this.lendTo(name, -debt);
            return;
        }

        this.owes.add(new Iou(name, debt));
    }

    public void lendTo(String name, double amount) {
        double debt = amount + popNetDebt(name);
        if (debt == 0) return;
        if (debt < 0) {
            this.borrowFrom(name, -debt);
            return;
        }

        this.owedBy.add(new Iou(name, debt));
    }

    public double balance() {
        double borrowed = this.owes.stream().mapToDouble(iou -> iou.amount).sum();
        double lent = this.owedBy.stream().mapToDouble(iou -> iou.amount).sum();
        return lent - borrowed;
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
        private final List<Iou> owes = new ArrayList<>();
        private final List<Iou> owedBy = new ArrayList<>();

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder owes(String name, double amount) {
            owes.add(new Iou(name, amount));
            return this;
        }

        public Builder owedBy(String name, double amount) {
            owedBy.add(new Iou(name, amount));
            return this;
        }

        public User build() {
            return new User(name, owes, owedBy);
        }
    }
}
