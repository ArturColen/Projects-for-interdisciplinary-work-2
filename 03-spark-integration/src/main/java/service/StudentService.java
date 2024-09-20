package service;

import java.util.Scanner;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.StudentDAO;
import model.Student;
import spark.Request;
import spark.Response;


public class StudentService {
	private StudentDAO studentDAO = new StudentDAO();
    private String form;
    private final int FORM_INSERT = 1;
    private final int FORM_DETAIL = 2;
    private final int FORM_UPDATE = 3;
    private final int FORM_ORDERBY_ID = 1;
    private final int FORM_ORDERBY_NAME = 2;
    private final int FORM_ORDERBY_COURSE = 3;
	
	
    public StudentService() {
        makeForm();
    }
    
    public void makeForm() {
        makeForm(FORM_INSERT, new Student(), FORM_ORDERBY_NAME);
    }

    public void makeForm(int orderBy) {
        makeForm(FORM_INSERT, new Student(), orderBy);
    }

    public void makeForm(int type, Student student, int orderBy) {
        String fileName = "form.html";
        form = "";
        
        try {
            Scanner input = new Scanner(new File(fileName));
            
            while (input.hasNext()) {
                form += (input.nextLine() + "\n");
            }
            
            input.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        String studentDetails = "";
        
        if (type != FORM_INSERT) {
            studentDetails += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/student/list/1\">New Student</a></b></font></td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t</table>";
            studentDetails += "\t<br>";
        }

        if (type == FORM_INSERT || type == FORM_UPDATE) {
            String action = "/student/";
            String name, email, phoneNumber, buttonLabel;
            
            if (type == FORM_INSERT) {
                action += "insert";
                name = "Add New Student";
                email = "email@example.com";
                phoneNumber = "(555) 123-4567";
                buttonLabel = "Add";
            } else {
                action += "update/" + student.getId();
                name = "Update Student (ID " + student.getId() + ")";
                email = student.getEmail();
                phoneNumber = student.getPhoneNumber();
                buttonLabel = "Update";
            }

            studentDetails += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            studentDetails += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + name + "</b></font></td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td>&nbsp;Name: <input class=\"input--register\" type=\"text\" name=\"name\" value=\"" + student.getName() + "\"></td>";
            studentDetails += "\t\t\t<td>Email: <input class=\"input--register\" type=\"text\" name=\"email\" value=\"" + email + "\"></td>";
            studentDetails += "\t\t\t<td>Phone: <input class=\"input--register\" type=\"text\" name=\"phoneNumber\" value=\"" + phoneNumber + "\"></td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td>&nbsp;Enrollment Date: <input class=\"input--register\" type=\"text\" name=\"enrollmentDate\" value=\"" + student.getEnrollmentDate().toString() + "\"></td>";
            studentDetails += "\t\t\t<td>Course: <input class=\"input--register\" type=\"text\" name=\"course\" value=\"" + student.getCourse() + "\"></td>";
            studentDetails += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t</table>";
            studentDetails += "\t</form>";
        } else if (type == FORM_DETAIL) {
            studentDetails += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Student Details (ID " + student.getId() + ")</b></font></td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td>&nbsp;Name: " + student.getName() + "</td>";
            studentDetails += "\t\t\t<td>Email: " + student.getEmail() + "</td>";
            studentDetails += "\t\t\t<td>Phone: " + student.getPhoneNumber() + "</td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t\t<tr>";
            studentDetails += "\t\t\t<td>&nbsp;Enrollment Date: " + student.getEnrollmentDate().toString() + "</td>";
            studentDetails += "\t\t\t<td>Course: " + student.getCourse() + "</td>";
            studentDetails += "\t\t\t<td>&nbsp;</td>";
            studentDetails += "\t\t</tr>";
            studentDetails += "\t</table>";
        } else {
            System.out.println("Erro. Type not identified: " + type);
        }

        form = form.replaceFirst("<STUDENT-DETAILS>", studentDetails);

        String list = "<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">";
        
        list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;List of Students</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/student/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/student/list/" + FORM_ORDERBY_NAME + "\"><b>Name</b></a></td>\n" +
                "\t<td><a href=\"/student/list/" + FORM_ORDERBY_COURSE + "\"><b>Course</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detail</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Update</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Delete</b></td>\n" +
                "</tr>\n";

        List<Student> students;
        
        if (orderBy == FORM_ORDERBY_ID) {
            students = studentDAO.getOrderById();
        } else if (orderBy == FORM_ORDERBY_NAME) {
            students = studentDAO.getOrderByName();
        } else if (orderBy == FORM_ORDERBY_COURSE) {
            students = studentDAO.getOrderById();
        } else {
            students = studentDAO.get();
        }

        int i = 0;
        String bgColor = "";
        
        for (Student s : students) {
            bgColor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            
            list += "\n<tr bgcolor=\"" + bgColor + "\">\n" +
                    "\t<td>" + s.getId() + "</td>\n" +
                    "\t<td>" + s.getName() + "</td>\n" +
                    "\t<td>" + s.getCourse() + "</td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/student/" + s.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"/student/update/" + s.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmDeleteStudent('" + s.getId() + "', '" + s.getName() + "', '" + s.getCourse() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "</tr>\n";
        }
        
        list += "</table>";
        form = form.replaceFirst("<LIST-STUDENT>", list);
    }
	
    public Object insert(Request request, Response response) {
        String name = request.queryParams("name");
        String email = request.queryParams("email");
        String phoneNumber = request.queryParams("phoneNumber");
        LocalDateTime enrollmentDate = LocalDateTime.parse(request.queryParams("enrollmentDate"));
        String course = request.queryParams("course");

        String resp = "";

        Student student = new Student(-1, name, email, phoneNumber, enrollmentDate, course);

        if (studentDAO.insert(student)) {
            resp = "Student (" + name + ") inserted!";
            response.status(201);
        } else {
            resp = "Student (" + name + ") not inserted!";
            response.status(404);
        }

        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Student student = studentDAO.get(id);

        if (student != null) {
            response.status(200);
            makeForm(FORM_DETAIL, student, FORM_ORDERBY_COURSE);
        } else {
            response.status(404);
            
            String resp = "Student " + id + " not found.";
            makeForm();
            
            form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", 
                                     "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }

	
    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Student student = studentDAO.get(id);

        if (student != null) {
            response.status(200);
            makeForm(FORM_UPDATE, student, FORM_ORDERBY_COURSE);
        } else {
            response.status(404);
            String resp = "Student " + id + " not found.";
            
            makeForm();
            form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", 
                                     "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
        }

        return form;
    }
	
    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        makeForm(orderBy);
        
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        
        return form;
    }
	
    public Object update(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Student student = studentDAO.get(id);
        String resp = "";

        if (student != null) {
            student.setName(request.queryParams("name"));
            student.setEmail(request.queryParams("email"));
            student.setPhoneNumber(request.queryParams("phoneNumber"));
            student.setEnrollmentDate(LocalDateTime.parse(request.queryParams("enrollmentDate")));
            student.setCourse(request.queryParams("course"));

            studentDAO.update(student);
            response.status(200);
            resp = "Student (ID " + student.getId() + ") updated!";
        } else {
            response.status(404);
            resp = "Student (ID " + id + ") not found!";
        }
        
        makeForm();
        
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }

	
    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Student student = studentDAO.get(id);
        String resp = "";

        if (student != null) {
            studentDAO.delete(id);
            response.status(200);
            resp = "Student (" + id + ") deleted!";
        } else {
            response.status(404);
            resp = "Student (" + id + ") not found!";
        }
        
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"" + resp + "\">");
    }
}