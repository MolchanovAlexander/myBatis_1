package com.example.demo.mocks;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.example.demo.beans.PostgreSQLShowCase;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;


/**
 *
 * @author salat
 */
@Component
public class MockCurrencyDataProcessing implements MockServlet{

    private static final Logger logger = Logger.getLogger(MockCurrencyDataProcessing.class.getSimpleName());
    private static final List<String> postgresValues = Arrays.asList("USD", "EUR");
    @Autowired
    private PostgreSQLShowCase postgreSQLShowCase;

    public MockCurrencyDataProcessing() {

    }
    @Override
    public byte[] doGet(Map<String, String> map) {

        byte[] data = new byte[0];
        try {
            String someParam = map.get("CODE");

            try {
                data = postgreSQLShowCase.getBodyByCode(someParam).getBytes();
            }catch (EmptyResultDataAccessException ignored) {
                logger.log(Level.WARNING, "not found");
            }

            if (data == null || data.length == 0) {
                JSONObject object = new JSONObject();
                object.put("message", "NONE");
                data = object.toString().getBytes();
            }

        } catch (Throwable ex) {
            logger.log(Level.SEVERE, "err mess", ex);
            throw new RuntimeException(ex);
        }

        return data;
    }
}
