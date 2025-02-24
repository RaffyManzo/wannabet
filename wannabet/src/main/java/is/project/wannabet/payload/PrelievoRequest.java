package is.project.wannabet.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PrelievoRequest {

    @NotBlank(message = "L'IBAN è obbligatorio")
    private String iban;

    @NotNull(message = "L'importo da prelevare è obbligatorio")
    @Positive(message = "L'importo deve essere maggiore di zero")
    private Double importo;

    // Getters e Setters
    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }
}
