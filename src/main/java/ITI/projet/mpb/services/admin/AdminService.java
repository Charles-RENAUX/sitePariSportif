package ITI.projet.mpb.services.admin;

public class AdminService {
    private static class AdminServiceHolder {
        private static AdminService instance = new AdminService();
    }

    public static AdminService getInstance() {
        return AdminServiceHolder.instance;
    }

    private AdminService() {
    }

}
