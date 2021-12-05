package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Queue;

import static advisor.Store.page;

public class CategoryPlaylistsDataCommand extends DataCommand {
  private String name;

  public CategoryPlaylistsDataCommand(Queue<Command> commands, String name) {
    super(commands);
    this.name = name;
  }

  @Override
  void execute() throws Exception {
    var response = Requester.fetch("categories", null, null);
    var json = JsonParser.parseString(response).getAsJsonObject();
    var error = JsonUtils.checkForErrorMessage(json);
    if (error.isPresent()) {
      System.out.println(error.get());
      commands.add(new UserInputCommand(commands));
      return;
    }
    var mainJsonObj = json.get("categories").getAsJsonObject();
    var props = JsonUtils.getMainProps(mainJsonObj);
    var categoryIdsMap = new HashMap<String, String>();
    for (var item : props.items) {
      var itemObj = item.getAsJsonObject();
      var name = itemObj.get("name").getAsString();
      var id = itemObj.get("id").getAsString();
      categoryIdsMap.put(name, id);
    }

    if (categoryIdsMap.get(name) == null) {
      System.out.println("Unknown category name.");
      commands.add(new UserInputCommand(commands));
      return;
    }

    var rootJsonObj = getRootJsonObjFromPath("categories/" + categoryIdsMap.get(name) + "/playlists");
    if (rootJsonObj == null)
      return;
    mainJsonObj = rootJsonObj.get("playlists").getAsJsonObject();
    this.props = JsonUtils.getMainProps(mainJsonObj);

    if (currentOffset == -1) {
      for (var item : this.props.items) {
        var itemObj = item.getAsJsonObject();
        printData(itemObj);
      }
    } else {
      for (int i = currentOffset; i < page + currentOffset; i++) {
        var itemObj = this.props.items.get(i).getAsJsonObject();
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
