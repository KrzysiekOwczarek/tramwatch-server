package tramwatch;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by krzysztofowczarek on 04/04/15.
 */

@RestController
public class HelloController {

    @PersistenceContext(unitName = "localDS")
    private EntityManager entityManager;

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

}
