package ing.hubs.spring.advanced.training.security.config;

public final class Roles {

    public static final String ADMIN_ROLE = "ADMIN";

    public static final String MANAGER_ROLE = "MANAGER";

    public static String getAdminRole() {
        return ADMIN_ROLE;
    }
}
