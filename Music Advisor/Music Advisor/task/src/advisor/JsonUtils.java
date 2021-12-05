package advisor;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.Optional;

public class JsonUtils {
  public static Optional<String> checkForErrorMessage(JsonObject jsonObject) {
    Optional<String> msg = Optional.empty();
    if (jsonObject.get("error") != null)
      msg = Optional.of(jsonObject.get("error").getAsJsonObject().get("message").getAsString());
    return msg;
  }

  public static MainProperties getMainProps(JsonObject jsonObject) {
    var props = new MainProperties();
    props.total = jsonObject.get("total").getAsInt();
    props.offset = jsonObject.get("offset").getAsInt();
    props.limit = jsonObject.get("limit").getAsInt();

    try {
      props.next = jsonObject.get("next").getAsString();
    } catch (UnsupportedOperationException e) {
      props.next = null;
    }

    try {
      props.prev = jsonObject.get("previous").getAsString();
    } catch (UnsupportedOperationException e) {
      props.prev = null;
    }

    props.items = jsonObject.get("items").getAsJsonArray();
    return props;
  }

  public static class MainProperties {
    public String next;
    public String prev;
    public int offset;
    public int total;
    public int limit;
    public JsonArray items;

    @Override
    public String toString() {
      return "MainProperties{" +
          "next='" + next + '\'' +
          ", prev='" + prev + '\'' +
          ", offset=" + offset +
          ", total=" + total +
          ", limit=" + limit +
          ", items=" + items +
          '}';
    }
  }
}
