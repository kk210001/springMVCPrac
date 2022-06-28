package hello.servlet.domain.web.frontController.v5;

import hello.servlet.domain.web.frontController.ModelView;
import hello.servlet.domain.web.frontController.MyView;
import hello.servlet.domain.web.frontController.v3.Controller.MemberFormControllerV3;
import hello.servlet.domain.web.frontController.v3.Controller.MemberListControllerV3;
import hello.servlet.domain.web.frontController.v3.Controller.MemberSaveControllerV3;
import hello.servlet.domain.web.frontController.v4.controller.MemberFormControllerV4;
import hello.servlet.domain.web.frontController.v4.controller.MemberListControllerV4;
import hello.servlet.domain.web.frontController.v4.controller.MemberSaveControllerV4;
import hello.servlet.domain.web.frontController.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.domain.web.frontController.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name="FrontControllerServlet5",urlPatterns = "/front-controller/v5/*")
public class FrontControllerServlet5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServlet5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    private void initHandlerAdapters() {
        //v3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        //v4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Object handler = getHandler(request);

        if (handler == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);//페이지 못찾음
            return;

        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);


        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);


        view.render(mv.getModel(),request,response);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾지 못했습니다 = " + handler);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURL = request.getRequestURI();
        return handlerMappingMap.get(requestURL);
    }
}
