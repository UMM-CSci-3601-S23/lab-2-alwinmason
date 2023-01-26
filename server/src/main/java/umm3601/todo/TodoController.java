package umm3601.todo;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

public class TodoController{
    private TodoDatabase database;

    public TodoController(TodoDatabase db){
        this.database = db;
    }

    public void getTodo(Context ctx) {
        String id = ctx.pathParam("id");
        Todo todo = database.getTodo(id);
        if (todo != null) {
          ctx.json(todo);
          ctx.status(HttpStatus.OK);
        } else {
          throw new NotFoundResponse("No todo with id " + id + " was found.");
        }
      }
    
    public void getTodos(Context ctx) {
        Todo[] todos = database.listTodos(ctx.queryParamMap());
        ctx.json(todos);
      }
}