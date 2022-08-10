package common.ejb;

import jakarta.ejb.Timer;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Collection;

@Named
@RequestScoped
public class TimerController {

    @Inject
    private ProgrammaticTimersManagerBean timerBean;

    public String cancelAllTimers() {
        timerBean.cancelAllTimers();;
        return "";
    }

        public void cancelTimer(Timer selectedTimer) {
        timerBean.cancelTimer(selectedTimer);
    }

    public Collection<Timer> list() {
        return timerBean.listAllTimers();
    }
}