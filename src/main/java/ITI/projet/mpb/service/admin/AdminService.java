package ITI.projet.mpb.service.admin;

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
