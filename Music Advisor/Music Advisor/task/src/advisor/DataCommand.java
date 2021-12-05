package advisor;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Queue;

import static advisor.Store.pagingStrategy;

public abstract class DataCommand extends Command {
  protected JsonUtils.MainProperties props = null;
  protected Paging paging = Paging.NONE;
  protected int currentOffset = -1;

  public DataCommand(Queue<Command> commands) {
    super(commands);
  }

  public JsonUtils.MainProperties getProps() {
    return props;
  }

  public void setProps(JsonUtils.MainProperties props) {
    this.props = props;
  }

  public Paging getPaging() {
    return paging;
  }

  public void setPaging(Paging paging) {
    this.paging = paging;
  }

  public enum Paging {
    NONE, NEXT, PREV
  }

  protected String handlePaging(String path) throws Exception {
    return pagingStrategy.handlePaging(path, this);
  }

  protected JsonObject getRootJsonObjFromPath(String path) throws Exception {
    var response = handlePaging(path);
    if (response == null)
      return null;

    var rootJsonObj = JsonParser.parseString(response).getAsJsonObject();
    var error = JsonUtils.checkForErrorMessage(rootJsonObj);

    if (error.isPresent()) {
      System.out.println(error.get());
      commands.add(new UserInputCommand(commands));
      return null;
    }

    return rootJsonObj;
  }

  public int getCurrentOffset() {
    return currentOffset;
  }

  public void setCurrentOffset(int currentOffset) {
    this.currentOffset = currentOffset;
  }

  protected void printPage() {
    pagingStrategy.printPage(this);
  }
}
