import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class RestApi {

    private final List<User> users = new ArrayList<>();

    RestApi(User... users) {
        this.users.addAll(List.of(users));
    }

    String get(String url) {
        return get(url, new JSONObject().put("users", new JSONArray()));
    }

    String get(String url, JSONObject payload) {
        if (!Objects.equals(url, "/users")) return "";

        return getUsers(payload.getJSONArray("users").toList().stream().map(Object::toString).toList());
    }

    String post(String url, JSONObject payload) {
        return switch (url) {
            case "/add" -> postUser(payload);
            case "/iou" -> {
                List<User> users = postIou(payload);
                yield getUsers(users.stream().map(User::name).toList());
            }
            default -> "";
        };
    }

    private String getUsers(List<String> filterUserNames) {
        var users = this.users.stream()
                .filter(user -> filterUserNames.contains(user.name()))
                .map(User::toJson)
                .toList();

        return new JSONObject()
                .put("users", users)
                .toString();
    }

    private String postUser(JSONObject payload) {
        if (!payload.has("user")) return "";

        User.Builder builder = User.builder().setName(payload.getString("user"));
        User user = builder.build();
        users.add(user);

        return user.toJson().toString();
    }

    private List<User> postIou(JSONObject payload) {
        if (!payload.has("lender") || !payload.has("borrower")) return List.of();

        User lender = this.getUser(payload.getString("lender"));
        User borrower = this.getUser(payload.getString("borrower"));
        double amount = payload.getDouble("amount");

        assert lender != null;
        assert borrower != null;

        borrower.borrowFrom(lender.name(), amount);
        lender.lendTo(borrower.name(), amount);

        return List.of(lender, borrower);
    }

    private User getUser(String name) {
        return this.users.stream().filter(user -> user.name().equals(name)).findFirst().orElse(null);
    }
}