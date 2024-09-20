package app;

import static spark.Spark.*;
import service.StudentService;

public class Application {
    private static StudentService studentService = new StudentService();
	
    public static void main(String[] args) {
        port(6789);
        
        staticFiles.location("/public");
        
        post("/student/insert", (request, response) -> studentService.insert(request, response));

        get("/student/:id", (request, response) -> studentService.get(request, response));
        
        get("/student/list/:orderby", (request, response) -> studentService.getAll(request, response));

        get("/student/update/:id", (request, response) -> studentService.getToUpdate(request, response));
        
        post("/student/update/:id", (request, response) -> studentService.update(request, response));
           
        get("/student/delete/:id", (request, response) -> studentService.delete(request, response));
    }
}