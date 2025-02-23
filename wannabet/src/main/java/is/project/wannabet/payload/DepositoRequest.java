package is.project.wannabet.payload;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class DepositoRequest {

    @NotBlank(message = "Il numero della carta è obbligatorio")
    @Size(min = 13, max = 19, message = "Il numero della carta deve essere tra 13 e 19 cifre")
    private String numeroCarta;

    @NotBlank(message = "Il titolare della carta è obbligatorio")
    @Size(max = 100, message = "Il nome del titolare non può superare i 100 caratteri")
    private String titolareCarta;

    @NotBlank(message = "La data di scadenza è obbligatoria")
    // Formato MM/yy, qui usato come stringa per semplicità
    private String dataScadenza;

    @NotBlank(message = "Il codice CVV è obbligatorio")
    @Size(min = 3, max = 4, message = "Il codice CVV deve essere di 3 o 4 cifre")
    private String cvv;

    @NotNull(message = "L'importo del deposito è obbligatorio")
    @Positive(message = "L'importo deve essere positivo")

    private Double importo;

    // Getters e Setters

    public String getNumeroCarta() {
        return numeroCarta;
    }

    public void setNumeroCarta(String numeroCarta) {
        this.numeroCarta = numeroCarta;
    }

    public String getTitolareCarta() {
        return titolareCarta;
    }

    public void setTitolareCarta(String titolareCarta) {
        this.titolareCarta = titolareCarta;
    }

    public String getDataScadenza() {
        return dataScadenza;
    }

    public void setDataScadenza(String dataScadenza) {
        this.dataScadenza = dataScadenza;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Double getImporto() {
        return importo;
    }

    public void setImporto(Double importo) {
        this.importo = importo;
    }
}
