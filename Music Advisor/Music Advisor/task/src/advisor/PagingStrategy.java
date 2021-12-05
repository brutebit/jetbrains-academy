package advisor;

public interface PagingStrategy {
  String handlePaging(String path, DataCommand command) throws Exception;
  void printPage(DataCommand command);
  void reset();
}
