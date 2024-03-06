package br.com.vini.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ParameterReader {

    private Map<String, Object> values;

    public ParameterReader(BufferedReader reader) throws IOException {
        StringBuilder data = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            data.append(line);
        }

        var parameters = data.toString().split("&");
        this.values = new HashMap<>(parameters.length);

        for (var parameter : parameters) {
            var entry = parameter.split("=");
            var key = entry[0];
            var value = entry[1];

            this.values.put(key, value);
        }
    }

    public Object getParameter(String key) {
        return this.values.get(key);
    }
}
