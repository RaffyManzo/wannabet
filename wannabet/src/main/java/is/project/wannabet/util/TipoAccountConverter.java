package is.project.wannabet.util;

import is.project.wannabet.model.TipoAccount;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class TipoAccountConverter implements AttributeConverter<TipoAccount, String> {

    @Override
    public String convertToDatabaseColumn(TipoAccount tipo) {
        if (tipo == null) {
            return null;
        }
        switch (tipo) {
            case UTENTE: return "Utente";
            case ADMIN: return "Admin";
            case GESTORE_FEDELTA: return "Gestore_fedeltá";
            case GESTORE_REFERTI: return "Gestore_referti";
            case GESTORE_EVENTO: return "Gestore_evento";
            default: throw new IllegalArgumentException("Tipo di account non supportato: " + tipo);
        }
    }

    @Override
    public TipoAccount convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        return Stream.of(TipoAccount.values())
                .filter(tipo -> tipo.name().equalsIgnoreCase(dbData.replace("é", "e")))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Tipo di account sconosciuto: " + dbData));
    }
}
