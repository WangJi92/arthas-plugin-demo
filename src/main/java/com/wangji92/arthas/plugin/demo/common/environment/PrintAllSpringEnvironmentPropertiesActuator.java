package com.wangji92.arthas.plugin.demo.common.environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * <p>intro </p>
 * <pre>
 *   {@code
 *       //code example
 *   }
 *  </pre>
 *
 * @author wangji
 * @date 2024/11/15 21:50
 */
@Slf4j
@Component
public class PrintAllSpringEnvironmentPropertiesActuator  implements CommandLineRunner, EnvironmentAware {

    private Environment environment;

    @Override
    public void run(String... args) throws Exception {
        try {
            printEnvironmentProperties();
        } catch (Exception e) {
            log.error("print env error", e);
        }
    }

    private void printEnvironmentProperties() {
        List<String> springConfigList = new LinkedList<String>();
        Iterator<PropertySource<?>> iterator = ((ConfigurableEnvironment) this.environment).getPropertySources().iterator();
        int order = 1;
        while (iterator.hasNext()) {
            PropertySource<?> source = iterator.next();
            String name = source.getName();
            springConfigList.add("order=" + order + "  --------------------env source name=" + name + "");
            Object o = source.getSource();
            if (o instanceof Map) {
                ((Map<String, Object>) o).forEach((key, value) -> springConfigList.add(key + "=" + this.environment.getProperty(key)));
            }
            order++;
        }
        String envListInfo = String.join("\n", springConfigList);
        log.info("spring current env:\n{}", envListInfo);
    }


    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}