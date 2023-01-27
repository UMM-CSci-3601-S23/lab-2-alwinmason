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
    // Set limit if desired
    if (queryParams.containsKey("limit")) {
      String targetCompany = queryParams.get("company").get(0);
      try {
        int entrepriseCiblé = Integer.parseInt(targetCompany);
        filteredTodos = setLimit(filteredTodos, entrepriseCiblé);
      }
      catch (NumberFormatException e) {
        throw new BadRequestResponse("Specified age '" + targetCompany + "' can't be parsed to an integer");
      }


    }
      return allTodos;
    }

    public Todo[] setLimit(Todo[] currentList, int limit){
      Todo[] limitedList = new Todo[limit];
      for (int i=0; i<limit;i++){
        limitedList[i] = currentList[i];
      }
      return limitedList;
    }
}
