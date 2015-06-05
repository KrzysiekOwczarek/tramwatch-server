package tramwatch.application;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import utils.SAXParser;

/**
 * Created by krzysztofowczarek on 03/06/15.
 */
@Configuration
public class AppConfig {

    @Bean
    public SAXParser getSAXParser(){
        SAXParser saxParser = new SAXParser();
        return saxParser;
    }
}
