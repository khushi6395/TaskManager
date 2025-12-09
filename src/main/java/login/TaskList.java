package login;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/TaskListServlet")
public class TaskList extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html><head><title>Task Manager</title>");

  
        out.println("<style>");
        out.println("body{margin:0;padding:0;background:#e8f1ff;font-family:Arial;"
                + "display:flex;justify-content:center;align-items:center;min-height:100vh;}");
        out.println(".container{width:380px;background:white;padding:20px;border-radius:14px;"
                + "box-shadow:0 4px 15px rgba(0,0,0,0.1);}");
        out.println("h2{text-align:center;color:#1a4cb3;margin-bottom:20px;}");
        out.println(".task-box{width:100%;height:320px;background:#f8fbff;border:2px solid #d0e2ff;"
                + "border-radius:12px;padding:10px;overflow-y:auto;}");
        out.println(".task{background:#e7f0ff;padding:10px;margin-bottom:10px;border-radius:8px;"
                + "display:flex;justify-content:space-between;align-items:center;}");
        out.println(".delete-btn{background:#ff4c4c;color:white;border:none;padding:5px 10px;"
                + "border-radius:6px;cursor:pointer;}");
        out.println(".bottom{margin-top:18px;display:flex;gap:10px;}");
        out.println("input[type=text]{flex:1;padding:10px;border:2px solid #bcd2ff;"
                + "border-radius:8px;outline:none;}");
        out.println(".add-btn{background:#1a73e8;color:white;border:none;padding:10px 18px;"
                + "border-radius:8px;cursor:pointer;font-weight:bold;}");
        out.println("</style></head><body>");

        out.println("<div class='container'>");
        out.println("<h2>Task Manager</h2>");

        out.println("<div class='task-box'>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/todos",
                    "root",
                    "*****"
            );

            PreparedStatement ps = con.prepareStatement("SELECT * FROM tasks");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String task = rs.getString("task");

                out.println("<div class='task'>");
                out.println("<span>" + task + "</span>");
                out.println("<form action='DeleteTaskServlet' method='post'>"
                        + "<input type='hidden' name='taskId' value='" + id + "'>"
                        + "<button class='delete-btn'>Delete</button>"
                        + "</form>");
                out.println("</div>");
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        out.println("</div>");

  
        out.println("<form class='bottom' method='post' action='AddTaskServlet'>"
                + "<input type='text' name='taskName' placeholder='Enter new task...' required>"
                + "<button class='add-btn'>Add</button>"
                + "</form>");

        out.println("</div></body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
