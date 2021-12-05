package advisor;

import com.google.gson.JsonObject;

import java.util.Queue;

import static advisor.Store.page;


public class FeaturedPlaylistsDataCommand extends DataCommand {
  public FeaturedPlaylistsDataCommand(Queue<Command> commands) {
    super(commands);
  }

  @Override
  void execute() throws Exception {
    var rootJsonObj = getRootJsonObjFromPath("featured-playlists");
    if (rootJsonObj == null)
      return;
    var mainJsonObj = rootJsonObj.get("playlists").getAsJsonObject();
    props = JsonUtils.getMainProps(mainJsonObj);

    if (currentOffset == -1) {
      for (var item : props.items) {
        var itemObj = item.getAsJsonObject();
        printData(itemObj);
      }
    } else {
      for (int i = currentOffset; i < page + currentOffset; i++) {
        var itemObj = props.items.get(i).getAsJsonObject();
        printData(itemObj);
      }
    }

    printPage();
    commands.add(new UserInputCommand(commands));
  }

  private void printData(JsonObject itemObj) {
    var name = itemObj.get("name").getAsString();
    var url = itemObj.get("external_urls").getAsJsonObject().get("spotify").getAsString();
    System.out.println(name);
    System.out.println(url + "\n");
  }
}
