package hello.servlet.domain.web.frontController.v2;

import hello.servlet.domain.web.frontController.MyView;
import hello.servlet.domain.web.frontController.v1.ControllerV1;
import hello.servlet.domain.web.frontController.v1.controller.MemberControllerV1;
import hello.servlet.domain.web.frontController.v1.controller.MemberListControllerV1;
import hello.servlet.domain.web.frontController.v1.controller.MemberSaveControllerV1;
import hello.servlet.domain.web.frontController.v2.Controller.MemberFormControllerV2;
import hello.servlet.domain.web.frontController.v2.Controller.MemberListControllerV2;
import hello.servlet.domain.web.frontController.v2.Controller.MemberSaveControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name="frontControllerServletV2",urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerV2Map = new HashMap<>();

    public FrontControllerServletV2() {
        controllerV2Map.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerV2Map.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerV2Map.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        String requestURL = request.getRequestURI();

        ControllerV2 controller = controllerV2Map.get(requestURL);
        if (controller == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);//페이지 못찾음
            return;
        }
        MyView view = controller.process(request, response);
        view.render(request,response);
    }
}
