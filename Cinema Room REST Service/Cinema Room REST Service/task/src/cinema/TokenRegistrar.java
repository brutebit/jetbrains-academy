package cinema;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class TokenRegistrar {
  private static final ConcurrentHashMap<Seat, String> registeredTokens = new ConcurrentHashMap<>();

  public static String generateToken(Seat seat) {
    if (registeredTokens.get(seat) != null)
      throw new Error("Already registered");
    var token = UUID.randomUUID().toString();
    if (registeredTokens.contains(token))
      return generateToken(seat);
    registeredTokens.put(seat, token);
    return token;
  }

  public static void unregisterToken(Seat seat) {
    registeredTokens.remove(seat);
  }

  public static String getToken(Seat seat) {
    return registeredTokens.get(seat);
  }

  public static Seat getSeat(String token) {
    for (var it = registeredTokens.keys().asIterator(); it.hasNext(); ) {
      var seat = it.next();
      if (registeredTokens.get(seat).equals(token))
        return seat;
    }
    return null;
  }
}
