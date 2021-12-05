package advisor;

import com.google.gson.JsonObject;

import java.util.Queue;

import static advisor.Store.page;

public class CategoriesDataCommand extends DataCommand {
  public CategoriesDataCommand(Queue<Command> commands) {
    super(commands);
  }

  @Override
  void execute() throws Exception {
    var rootJsonObj = getRootJsonObjFromPath("categories");
    if (rootJsonObj == null)
      return;
    var mainJsonObj = rootJsonObj.get("categories").getAsJsonObject();
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
    System.out.println(name);
  }
}
