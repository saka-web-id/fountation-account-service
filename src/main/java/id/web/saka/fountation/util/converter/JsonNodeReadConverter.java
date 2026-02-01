package id.web.saka.fountation.util.converter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.r2dbc.postgresql.codec.Json;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

@ReadingConverter
public class JsonNodeReadConverter implements Converter<Json, JsonNode> {
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public JsonNode convert(Json source) {
        try {
            return mapper.readTree(source.asString());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid JSON", e);
        }
    }
}


