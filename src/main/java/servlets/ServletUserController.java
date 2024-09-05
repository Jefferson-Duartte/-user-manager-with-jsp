package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import dao.DAOUserRepository;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import model.Login;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import java.io.IOException;
import java.util.List;

@MultipartConfig
@WebServlet("/ServletUserController")
public class ServletUserController extends ServletGenericUtil {

    String msg = "Operação realizada com sucesso!";

    private DAOUserRepository daoUserRepository = new DAOUserRepository();

    public ServletUserController() {
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String action = request.getParameter("urlAction");

            if (action != null && !action.isEmpty()) {

                if (action.equalsIgnoreCase("delete")) {

                    Long userId = Long.parseLong(request.getParameter("id"));
                    daoUserRepository.deleteUser(userId);

                    msg = "Usuário deletado com sucesso!";
                    request.setAttribute("msg", msg);

                    List<Login> listUsers = daoUserRepository.getAllUsers(super.getIdLoggedUser(request));
                    request.setAttribute("allUsers", listUsers);

                    request.getRequestDispatcher("main/create-user.jsp").forward(request, response);


                } else if (action.equalsIgnoreCase("searchUserAjax")) {
                    String name = request.getParameter("name");

                    List<Login> users = daoUserRepository.searchUsersByName(name, super.getIdLoggedUser(request));

                    ObjectMapper mapper = new ObjectMapper();
                    String json = mapper.writeValueAsString(users);

                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(json);

                    return;

                } else if (action.equalsIgnoreCase("editSearch")) {
                    Long id = Long.parseLong(request.getParameter("id"));

                    Login user = daoUserRepository.searchUserById(id);

                    List<Login> listUsers = daoUserRepository.getAllUsers(super.getIdLoggedUser(request));
                    request.setAttribute("allUsers", listUsers);

                    request.setAttribute("msg", msg);
                    request.setAttribute("dataLogin", user);
                    request.getRequestDispatcher("main/create-user.jsp").forward(request, response);

                    return;

                } else if (action.equalsIgnoreCase("getUsers")) {

                    List<Login> users = daoUserRepository.getAllUsers(super.getIdLoggedUser(request));

                    List<Login> listUsers = daoUserRepository.getAllUsers(super.getIdLoggedUser(request));
                    request.setAttribute("allUsers", listUsers);

                    request.setAttribute("msg", msg);
                    request.setAttribute("allUsers", users);
                    request.getRequestDispatcher("main/create-user.jsp").forward(request, response);

                } else if (action.equalsIgnoreCase("downloadUserPhoto")) {

                    String idUser = request.getParameter("id");

                    Login user = daoUserRepository.searchUserById(Long.parseLong(idUser));

                    if(user.getPhotoUser() != null || !user.getPhotoUser().isEmpty()){
                        response.setHeader("Content-Disposition", "attachment;filename=" + user.getLogin() + "." + user.getPhotoUserExtension());
                        response.getOutputStream().write(Base64.decodeBase64(user.getPhotoUser().split("\\,")[1]));
                    }

                }
            } else {
                request.getRequestDispatcher("main/create-user.jsp").forward(request, response);
            }


        } catch (Exception e) {
            e.printStackTrace();
            msg = "Ocorreu um erro: " + e.getMessage();
            request.setAttribute("msg", msg);
            RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
            redirect.forward(request, response);
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            String profile = request.getParameter("profile");
            String gender = request.getParameter("gender");
            String zipCode = request.getParameter("zipCode");
            String street = request.getParameter("street");
            String neighborhood = request.getParameter("neighborhood");
            String city = request.getParameter("city");
            String state = request.getParameter("state");
            String number = request.getParameter("number");

            Login user = new Login();
            user.setId(id != null && !id.isEmpty() ? Long.parseLong(id) : null);
            user.setName(name);
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);
            user.setProfile(profile);
            user.setGender(gender);
            user.setZipCode(zipCode);
            user.setStreet(street);
            user.setNeighborhood(neighborhood);
            user.setCity(city);
            user.setState(state);
            user.setNumber(number);

            if(ServletFileUpload.isMultipartContent(request)){
                Part part = request.getPart("filePhoto");
                if (part.getSize() > 0){
                    byte[] photo = IOUtils.toByteArray(part.getInputStream());
                    String photoBase64 = "data:" + part.getContentType() + ";base64," + new Base64().encodeBase64String(photo);
                    user.setPhotoUser(photoBase64);
                    user.setPhotoUserExtension(part.getContentType().split("\\/")[1]);
                }
            }

            if (daoUserRepository.validateLogin(user.getLogin()) && user.getId() == null) {
                msg = "Já existe um usuário com o mesmo login.";
            } else {
                if (user.isNew()) {
                    msg = "Usuário cadastrado com sucesso!";
                } else {
                    msg = "Usuário atualizado com sucesso!";
                }
                user = daoUserRepository.saveUser(user, super.getIdLoggedUser(request));
            }

            List<Login> listUsers = daoUserRepository.getAllUsers(super.getIdLoggedUser(request));
            request.setAttribute("allUsers", listUsers);
            request.setAttribute("msg", msg);
            request.setAttribute("dataLogin", user);

            request.getRequestDispatcher("main/create-user.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            msg = "Ocorreu um erro: " + e.getMessage();
            request.setAttribute("msg", msg);
            RequestDispatcher redirect = request.getRequestDispatcher("/error.jsp");
            redirect.forward(request, response);
        }
    }
}
