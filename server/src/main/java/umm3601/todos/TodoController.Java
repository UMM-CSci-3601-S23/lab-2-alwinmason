package umm3601.todos;

import io.javalin.http.Context;
import io.javalin.http.HttpStatus;
import io.javalin.http.NotFoundResponse;

public class TodoController{
    private TodoDatabase database;

    public TodoController(TodoDatabase db){
        this.database = db;
    }
    
    public void getTodos(Context ctx) {
        Todo[] todos = database.listTodos(ctx.queryParamMap());
        ctx.json(todos);
      }
}