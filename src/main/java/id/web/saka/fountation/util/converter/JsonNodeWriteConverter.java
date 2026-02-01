package id.web.saka.fountation.util.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

@WritingConverter
public class JsonNodeWriteConverter implements Converter<JsonNode, Json> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public Json convert(JsonNode source) {
        try {
            return Json.of(mapper.writeValueAsString(source));
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }
}

