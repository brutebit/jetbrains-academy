package advisor;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Queue;

import static advisor.JsonUtils.getMainProps;
import static advisor.Store.page;


public class NewReleasesDataCommand extends DataCommand {
  public NewReleasesDataCommand(Queue<Command> commands) {
    super(commands);
  }

  @Override
  void execute() throws Exception {
    var rootJsonObj = getRootJsonObjFromPath("new-releases");
    if (rootJsonObj == null)
      return;
    var mainJsonObj = rootJsonObj.get("albums").getAsJsonObject();
    props = getMainProps(mainJsonObj);

    if (currentOffset == -1) {
      for (var item : props.items) {
        var itemObj = item.getAsJsonObject();
        if (!itemObj.get("album_type").getAsString().equals("single")) {
          continue;
        }
        printData(itemObj);
      }
    } else {
      for (int i = currentOffset; i < page + currentOffset; i++) {
        var itemObj = props.items.get(i).getAsJsonObject();
        if (!itemObj.get("album_type").getAsString().equals("single")) {
          continue;
        }
        printData(itemObj);
      }
    }

    printPage();
    commands.add(new UserInputCommand(commands));
  }

  private void printData(JsonObject itemObj) {
    var name = itemObj.get("name").getAsString();
    var url = itemObj.get("external_urls").getAsJsonObject().get("spotify").getAsString();
    var artistNames = new ArrayList<String>();
    var artists = itemObj.get("artists").getAsJsonArray();
    for (var ar : artists) {
      artistNames.add(ar.getAsJsonObject().get("name").getAsString());
    }
    System.out.println(name);
    System.out.println("[" + String.join(", ", artistNames) + "]");
    System.out.println(url + "\n");
  }
}
