package umm3601.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

import umm3601.Server;


@SuppressWarnings({ "MagicNumber" })
public class TodoControllerSpec {

  private Context ctx = mock(Context.class);

  private TodoController todoController;
  private static TodoDatabase db;

  @BeforeEach
  public void setUp() throws IOException {
    db = new TodoDatabase(Server.TODO_DATA_FILE);
    todoController = new TodoController(db);
  }
  @Test
  public void canGetAllTodo() throws IOException {
    todoController.getTodos(ctx);
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    assertEquals(db.size(), argument.getValue().length);
  }

  @Test
  public void canGetTodoWithSpecifiedId() throws IOException {
    String id = "58895985a22c04e761776d54";
    Todo todo = db.getTodo(id);

    when(ctx.pathParam("id")).thenReturn(id);

    todoController.getTodo(ctx);

    verify(ctx).json(todo);
    verify(ctx).status(HttpStatus.OK);
    assertEquals("Blanche", todo.owner);
  }
  @Test
  public void respondsAppropriatelyToRequestForNonexistentId() throws IOException {
    when(ctx.pathParam("id")).thenReturn(null);
    Throwable exception = Assertions.assertThrows(NotFoundResponse.class, () -> {
      todoController.getTodo(ctx);
    });
    assertEquals("No todo with id " + null + " was found.", exception.getMessage());
  }
  @Test
  public void testFilterByOwner() {
    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("owner", Arrays.asList(new String[] {"Fry"}));
    when(ctx.queryParamMap()).thenReturn(queryParams);
    todoController.getTodos(ctx);
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    for (Todo user : argument.getValue()) {
      assertEquals("Fry", user.owner);
    }
    assertEquals(61, argument.getValue().length);
  }

  @Test
  public void canGetLimitedNumberOfTodos() throws IOException {
    Map<String, List<String>> queryParams = new HashMap<>();
    queryParams.put("limit", Arrays.asList(new String[] {"7"}));
    todoController.getTodos(ctx);
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    System.out.println(argument.getValue().length);
    assertEquals(7, argument.getValue().length);
  }


}
