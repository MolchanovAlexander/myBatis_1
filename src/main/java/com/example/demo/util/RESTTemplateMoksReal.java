package com.example.demo.util;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.demo.mocks.MockServlet;
import com.example.demo.service.NBUParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class RESTTemplateMoksReal implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        RESTTemplateMoksReal.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
    private static ClassLoaderForMocks classLoaderForMocks = new ClassLoaderForMocks();
    private static final Logger logger = Logger.getLogger("Template");
    private static NBUParser parser;
    @Autowired
    ApplicationContext ac;

    public RESTTemplateMoksReal(NBUParser parser) {
        this.parser = parser;
    }

    public byte[] doGet(String uri, Map<String, String> prs) {
        try {
            MockServlet mockInstance = getMockInstance(uri, ac);
            if (mockInstance == null) {
                String code = prs.get("CODE");
                byte[] bytes = parser.findData(code).toString().getBytes();
                logger.log(Level.INFO, String.format("Response: %s", new String(bytes, StandardCharsets.UTF_8)));
                return bytes;
            } else {
                byte[] bytes = mockInstance.doGet(prs);
                logger.log(Level.INFO, String.format("Response: %s", new String(bytes, StandardCharsets.UTF_8)));
                return bytes;
            }
        } catch (Exception e) {
            System.out.println("----------------DNNNOOOO___________________________" + e.getMessage());
        }
        return new byte[1];
    }

    private MockServlet getMockInstance(String uri, ApplicationContext ctx) {
        if (uri.startsWith("mock://")) {
            try {
                String mockClassName = uri.substring(7);
                Class<?> clazz = classLoaderForMocks.loadClass(mockClassName);
                Object instance = clazz.getDeclaredConstructor().newInstance();

                // 🧩 Inject dependencies managed by Spring
                ctx.getAutowireCapableBeanFactory().autowireBean(instance);

                return (MockServlet) instance;
            } catch (Exception ex) {
                throw new RuntimeException("Failed to create mock instance", ex);
            }
        } else {
            return null;
        }
    }

}
