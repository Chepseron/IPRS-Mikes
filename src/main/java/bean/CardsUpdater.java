
import java.util.Date;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.transaction.UserTransaction;

public class CardsUpdater
        implements ServletContextListener {

    @PersistenceContext(name = "com.mycompany_cardMaven_war_1.0-SNAPSHOTPU")
    private EntityManager em;
    @Resource
    public UserTransaction utx;
    private ScheduledExecutorService scheduler = null;

    public void contextInitialized(ServletContextEvent sce) {

        if ((scheduler == null) || (!scheduler.isTerminated())) {
            scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleAtFixedRate(new ScheduledTask(), 0, 6, TimeUnit.HOURS);
            System.out.println("Scheduler initializing successfully " + new Date());
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
        try {
            System.out.println("Scheduler Shutting down successfully " + new Date());
            scheduler.shutdown();
        } catch (Exception ex) {
        }
    }

    class ScheduledTask extends TimerTask {

        public void run() {
            try {

                utx.begin();
                em.createNativeQuery("LOAD DATA LOCAL INFILE 'D:/cards/Card List.txt' INTO TABLE  Cards FIELDS TERMINATED BY '|' LINES TERMINATED BY '\\n'").executeUpdate();
                utx.commit();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

    }
}
