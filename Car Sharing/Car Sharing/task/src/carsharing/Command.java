package carsharing;

import java.util.Queue;

public interface Command {
  void execute(Queue<Command> commands) throws Exception;
}
