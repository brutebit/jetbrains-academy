package server;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FileIdMap implements Serializable {
  private static final long serialVersionUID = 7L;
  private final Random random = new Random();

  HashMap<Integer, String> map = new HashMap<>();

  public String get(int id) {
    return map.get(id);
  }

  public int put(String filename) {
    int id = random.nextInt(100);
    map.put(id, filename);
    return id;
  }

  public void remove(int id) {
    map.remove(id);
  }

  public Integer getKey(String filename) {
    for (Map.Entry<Integer, String> entry : map.entrySet()) {
      if (filename.equals(entry.getValue())) {
        return entry.getKey();
      }
    }
    return null;
  }
}
