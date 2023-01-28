package umm3601.todo;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.BadRequestResponse;

public class TodoDatabase{
    private Todo[] allTodos;

    public TodoDatabase(String todoDataFile) throws IOException {
        InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
        ObjectMapper objectMapper = new ObjectMapper();
        allTodos = objectMapper.readValue(reader, Todo[].class);
    }
    //get user with a specific ID
    public Todo getTodo(String id) {
        return Arrays.stream(allTodos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
  }

    // returns all todos
    public Todo[] listTodos(Map<String, List<String>> queryParams){
      Todo[] filteredTodos = allTodos;
    if (queryParams.containsKey("status")){
      //filter by status
      List<String> givenStatus = queryParams.get("status");
        //checks what was given as a query and converts into the corresponding boolean
        //then filters by status
        if (givenStatus.contains("complete")) { 
          boolean targetStatus = true; 
          filteredTodos = filterTodoByStatus(filteredTodos, targetStatus);
        }

        else {
          boolean targetStatus = false; 
          filteredTodos = filterTodoByStatus(filteredTodos, targetStatus);
        } 
    }
    //filtering by todos bodies which contain a given string
    if (queryParams.containsKey("body")){
      String givenString = queryParams.get("body").get(0);
        filteredTodos = filterTodoByBody(filteredTodos, givenString);
    }
      
    
    // Set limit if desired
    if (queryParams.containsKey("limit")) {
      String targetCompany = queryParams.get("limit").get(0);
      //int eb = Integer.parseInt(targetCompany);
      //System.out.println(eb);
      try {
        int entrepriseCiblé = Integer.parseInt(targetCompany);
        filteredTodos = setLimit(filteredTodos, entrepriseCiblé);
      }
      catch (NumberFormatException e) {
        throw new BadRequestResponse("Specified age '" + targetCompany + "' can't be parsed to an integer");
      }
    }
      return filteredTodos;
    }

    public Todo[] setLimit(Todo[] currentList, int limit){
      Todo[] bruh = Arrays.stream(currentList, 0, limit).toArray(Todo[]::new);
      for (int i = 0; i < limit; i++){
        System.out.println(bruh[i]);
      }
      return Arrays.stream(currentList, 0, limit).toArray(Todo[]::new);
    }

    public Todo[] filterTodoByStatus(Todo[] todos, boolean givenStatus) {
      return Arrays.stream(todos).filter(x -> x.status == givenStatus).toArray(Todo[]::new);
    }

    public Todo[] filterTodoByBody(Todo[] todos, String givenString) {
      return Arrays.stream(todos).filter(x -> x.body.contains(givenString)).toArray(Todo[]::new);
    }
    
  } 
