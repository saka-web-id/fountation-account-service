package id.web.saka.fountation.util.mapper;

import com.google.protobuf.Timestamp;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class DateTimeMapper {

    private static final ZoneId DEFAULT_ZONE = ZoneId.of("Asia/Jakarta"); // GMT+7

    // Instant → ZonedDateTime (GMT+7)
    public ZonedDateTime toOffset(Instant instant) {
        return instant == null ? null : instant.atZone(DEFAULT_ZONE);
    }

    // ZonedDateTime (GMT+7) → Instant (UTC)
    public Instant toInstant(ZonedDateTime zdt) {
        return zdt == null ? null : zdt.toInstant();
    }

    // Protobuf Timestamp → ZonedDateTime (GMT+7)
    @Named("toZonedDateTime")
    public ZonedDateTime toZonedDateTime(Timestamp ts) {
        if (ts == null || (ts.getSeconds() == 0 && ts.getNanos() == 0)) return null;
        return ZonedDateTime.ofInstant(Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos()), DEFAULT_ZONE);
    }

    // ZonedDateTime (GMT+7) → Protobuf Timestamp
    @Named("toProtoTimestamp")
    public Timestamp toProtoTimestamp(ZonedDateTime zdt) {
        if (zdt == null) return Timestamp.getDefaultInstance();
        Instant instant = zdt.toInstant();
        return Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }

    // Protobuf Timestamp → Instant
    @Named("toInstant")
    public Instant toInstant(Timestamp ts) {
        if (ts == null || (ts.getSeconds() == 0 && ts.getNanos() == 0)) return null;
        return Instant.ofEpochSecond(ts.getSeconds(), ts.getNanos());
    }



    @Named("instantToTimestamp")
    public com.google.protobuf.Timestamp instantToTimestamp(Instant instant) {
        if (instant == null) return com.google.protobuf.Timestamp.getDefaultInstance();
        return com.google.protobuf.Timestamp.newBuilder()
                .setSeconds(instant.getEpochSecond())
                .setNanos(instant.getNano())
                .build();
    }
}
