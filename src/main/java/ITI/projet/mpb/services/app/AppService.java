package ITI.projet.mpb.services.app;

public class AppService {
    private static class AdminServiceHolder {
        private static AppService instance = new AppService();
    }

    public static AppService getInstance() {
        return AdminServiceHolder.instance;
    }

    private AppService() {
    }

}
