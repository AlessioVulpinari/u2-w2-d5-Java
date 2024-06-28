package alessiovulpinari.u2_w2_d5_Java.payloads;

import java.time.LocalDateTime;

public record ErrorPayload(String errorMessage, LocalDateTime dateTime) {
}
