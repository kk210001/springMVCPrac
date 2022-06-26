package hello.servlet.domain.web.frontController.v3;

import hello.servlet.domain.web.frontController.ModelView;

import java.util.Map;

public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);

}
