package hello.servlet.domain.web.frontController.v3.Controller;

import hello.servlet.domain.web.frontController.ModelView;
import hello.servlet.domain.web.frontController.v3.ControllerV3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramMap) {

        return new ModelView("new-form");

    }
}
