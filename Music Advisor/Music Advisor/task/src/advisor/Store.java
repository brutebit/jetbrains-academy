package advisor;

public class Store {
  public static boolean isLoggedIn = false;
  public static final String clientId = "8add7e634f744b8485f64cddb64e88b0";
  public static final String clientSecret = "2600a28414274f41b9ec972fcc3df7c5";
  public static String redirectUri = "http://localhost:8080";
  public static String resourceUri = "https://api.spotify.com";
  public static PagingStrategy pagingStrategy = new ApiPagingStrategy();
  public static String serverPath = "https://accounts.spotify.com";
  public static String code = "";
  public static String accessToken = "";
  public static int page = 5;
}
