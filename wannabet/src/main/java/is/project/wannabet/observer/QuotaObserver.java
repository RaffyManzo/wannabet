package is.project.wannabet.observer;

/**
 * Interfaccia per gli observer che monitorano le modifiche alle quote.
 * Ogni classe che implementa questa interfaccia verrà notificata quando
 * cambia lo stato di una quota.
 */
public interface QuotaObserver {

    /**
     * Metodo chiamato quando cambia lo stato di una quota.
     *
     * @param idQuota ID della quota modificata.
     */
    void update(Long idQuota);
}
