package is.project.wannabet.config;

import is.project.wannabet.observer.QuotaManager;
import is.project.wannabet.observer.ScommessaObserverManager;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObserverConfig {

    @Autowired
    private QuotaManager quotaManager;

    @Autowired
    private ScommessaObserverManager scommessaObserverManager;

    @PostConstruct
    public void initObservers() {
        quotaManager.addObserver(scommessaObserverManager);
    }
}
