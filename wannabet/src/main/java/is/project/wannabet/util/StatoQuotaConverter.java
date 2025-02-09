package is.project.wannabet.util;

import is.project.wannabet.model.StatoQuota;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class StatoQuotaConverter implements AttributeConverter<StatoQuota, String> {

    @Override
    public String convertToDatabaseColumn(StatoQuota stato) {
        if (stato == null) {
            return null;
        }
        switch (stato) {
            case DA_REFERTARE:
                return "Da Refertare";
            case VINCENTE:
                return "Vincente";
            case PERDENTE:
                return "Perdente";
            default:
                throw new IllegalArgumentException("Stato sconosciuto: " + stato);
        }
    }

    @Override
    public StatoQuota convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        switch (dbData) {
            case "Da Refertare":
                return StatoQuota.DA_REFERTARE;
            case "Vincente":
                return StatoQuota.VINCENTE;
            case "Perdente":
                return StatoQuota.PERDENTE;
            default:
                throw new IllegalArgumentException("Valore sconosciuto nel DB: " + dbData);
        }
    }
}
