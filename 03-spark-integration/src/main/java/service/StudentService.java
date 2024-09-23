package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
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

        String oneStudent = "";
        
        if (type != FORM_INSERT) {
            oneStudent += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            oneStudent += "\t\t<tr>";
            oneStudent += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/student/list/1\">Novo Aluno</a></b></font></td>";
            oneStudent += "\t\t</tr>";
            oneStudent += "\t</table>";
            oneStudent += "\t<br>";
        }

        if (type == FORM_INSERT || type == FORM_UPDATE) {
            String action = "/student/";
            String title, name, buttonLabel;
            
            if (type == FORM_INSERT) {
                action += "insert";
                title = "Adicionar Novo Aluno";
                name = "";
                buttonLabel = "Adicionar";
            } else {
                action += "update/" + student.getId();
                title = "Update Student (ID " + student.getId() + ")";
                name = student.getName();
                buttonLabel = "Atualizar";
            }
            
            oneStudent += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
            oneStudent += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            oneStudent += "\t\t<tr>";
            oneStudent += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + title + "</b></font></td>";
            oneStudent += "\t\t</tr>";
            oneStudent += "\t\t<tr>";
            oneStudent += "\t\t\t<td>&nbsp;Nome: <input class=\"input--register\" type=\"text\" name=\"name\" value=\"" + name + "\"></td>";
            oneStudent += "\t\t\t<td>E-mail: <input class=\"input--register\" type=\"text\" name=\"email\" value=\"" + student.getEmail() + "\"></td>";
            oneStudent += "\t\t\t<td>Telefone: <input class=\"input--register\" type=\"text\" name=\"phoneNumber\" value=\"" + student.getPhoneNumber() + "\"></td>";
            oneStudent += "\t\t</tr>";
            oneStudent += "\t\t<tr>";
            oneStudent += "\t\t\t<td>&nbsp;Data de Matrícula: <input class=\"input--register\" type=\"text\" name=\"enrollmentDate\" value=\"" + student.getEnrollmentDate().toString() + "\"></td>";
            oneStudent += "\t\t\t<td>Curso: <input class=\"input--register\" type=\"text\" name=\"course\" value=\"" + student.getCourse() + "\"></td>";
            oneStudent += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\"" + buttonLabel + "\" class=\"input--main__style input--button\"></td>";
            oneStudent += "\t\t</tr>";
            oneStudent += "\t</table>";
            oneStudent += "\t</form>";
        } else if (type == FORM_DETAIL) {
            oneStudent += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
            oneStudent += "\t\t<tr>";
            oneStudent += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhes do Aluno (ID " + student.getId() + ")</b></font></td>";
            oneStudent += "\t\t</tr>";
            oneStudent += "\t\t<tr>";
            oneStudent += "\t\t\t<td>&nbsp;Nome: " + student.getName() + "</td>";
            oneStudent += "\t\t\t<td>E-mail: " + student.getEmail() + "</td>";
            oneStudent += "\t\t\t<td>Telefone: " + student.getPhoneNumber() + "</td>";
            oneStudent += "\t\t</tr>";
            oneStudent += "\t\t<tr>";
            oneStudent += "\t\t\t<td>&nbsp;Data de Matrícula: " + student.getEnrollmentDate().toString() + "</td>";
            oneStudent += "\t\t\t<td>Curso: " + student.getCourse() + "</td>";
            oneStudent += "\t\t\t<td>&nbsp;</td>";
            oneStudent += "\t\t</tr>";
            oneStudent += "\t</table>";
        } else {
            System.out.println("ERROR! Unidentified type " + type);
        }
        form = form.replaceFirst("<ONE-STUDENT>", oneStudent);

        String list = "<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">";
        
        list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Lista de Alunos</b></font></td></tr>\n" +
                "\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
                "\n<tr>\n" +
                "\t<td><a href=\"/student/list/" + FORM_ORDERBY_ID + "\"><b>ID</b></a></td>\n" +
                "\t<td><a href=\"/student/list/" + FORM_ORDERBY_NAME + "\"><b>Nome</b></a></td>\n" +
                "\t<td><a href=\"/student/list/" + FORM_ORDERBY_COURSE + "\"><b>Curso</b></a></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Detalhes</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
                "\t<td width=\"100\" align=\"center\"><b>Deletar</b></td>\n" +
                "</tr>\n";

        List<Student> students;
        
        if (orderBy == FORM_ORDERBY_ID) {
            students = studentDAO.getOrderByID();
        } else if (orderBy == FORM_ORDERBY_NAME) {
            students = studentDAO.getOrderByName();
        } else if (orderBy == FORM_ORDERBY_COURSE) {
            students = studentDAO.getOrderByCourse();
        } else {
            students = studentDAO.get();
        }

        int i = 0;
        String bgcolor;
        
        for (Student s : students) {
            bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
            
            list += "\n<tr bgcolor=\"" + bgcolor + "\">\n" +
                    "\t<td>" + s.getId() + "</td>\n" +
                    "\t<td>" + s.getName() + "</td>\n" +
                    "\t<td>" + s.getCourse() + "</td>\n" +
                    "\t<td align=\"center\"><a href=\"/student/" + s.getId() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\"><a href=\"/student/update/" + s.getId() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "\t<td align=\"center\"><a href=\"javascript:confirmDeleteStudent('" + s.getId() + "', '" + s.getName() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
                    "</tr>\n";
        }
        
        list += "</table>";
        form = form.replaceFirst("<LIST-STUDENT>", list);
    }
    
    public Object insert(Request request, Response response) {
        String name = request.queryParams("name");
        String email = request.queryParams("email");
        String phoneNumber = request.queryParams("phoneNumber");
        LocalDate enrollmentDate = LocalDate.parse(request.queryParams("enrollmentDate"));
        String course = request.queryParams("course");

        String resp = "";

        Student student = new Student(-1, name, email, phoneNumber, enrollmentDate, course);

        if (studentDAO.insert(student)) {
            resp = "Aluno (" + name + ") adicionado com sucesso!";
            response.status(201);
        } else {
            resp = "Aluno (" + name + ") não pôde ser adicionado!";
            response.status(404);
        }

        makeForm();
        form = form.replaceFirst("value=\"\"", "value=\"" + resp + "\"");

        return form;
    }
    
    public Object get(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Student student = (Student) studentDAO.get(id);

        if (student != null) {
            response.status(200);
            makeForm(FORM_DETAIL, student, FORM_ORDERBY_NAME);
        } else {
            response.status(404);
            String resp = "Aluno " + id + " não encontrado.";
            makeForm();
            form = form.replaceFirst("<RESULT-OP>", resp);
        }

        return form;
    }
    
    public Object getToUpdate(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Student student = (Student) studentDAO.get(id);

        if (student != null) {
            response.status(200);
            makeForm(FORM_UPDATE, student, FORM_ORDERBY_NAME);
        } else {
            response.status(404);
            String resp = "Aluno " + id + " não encontrado.";
            makeForm();
            form = form.replaceFirst("<RESULT-OP>", resp);
        }

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
            student.setEnrollmentDate(LocalDate.parse(request.queryParams("enrollmentDate")));
            student.setCourse(request.queryParams("course"));

            studentDAO.update(student);
            response.status(200);
            resp = "Aluno (ID " + student.getId() + ") atualizado!";
        } else {
            response.status(404);
            resp = "Aluno (ID " + id + ") não pôde ser atualizado!";
        }

        makeForm();
        
        form = form.replaceFirst("value=\"\"", "value=\"" + resp + "\"");

        return form;
    }
    
    public Object delete(Request request, Response response) {
        int id = Integer.parseInt(request.params(":id"));
        Student student = studentDAO.get(id);

        String resp = "";

        if (student != null) {
            studentDAO.delete(id);
            response.status(200);
            resp = "Aluno (" + id + ") excluído!";
        } else {
            response.status(404);
            resp = "Aluno (" + id + ") não pôde ser excluído!";
        }

        makeForm();

        form = form.replaceFirst("value=\"\"", "value=\"" + resp + "\"");

        return form;
    }
    
    public Object getAll(Request request, Response response) {
        int orderBy = Integer.parseInt(request.params(":orderby"));
        
        makeForm(orderBy);
        
        response.header("Content-Type", "text/html");
        response.header("Content-Encoding", "UTF-8");
        
        return form;
    }
}
