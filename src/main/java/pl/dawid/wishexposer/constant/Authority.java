package pl.dawid.wishexposer.constant;

public class Authority {
    public static final String[] USER_AUTHORITIES = { "user:read"/*, "patient:read"*/ }; //user can read user information, patient can read patient information
    public static final String[] VIP_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] MANAGER_AUTHORITIES = { "user:read", "user:update" };
    public static final String[] ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update" };
    public static final String[] SUPER_ADMIN_AUTHORITIES = { "user:read", "user:create", "user:update", "user:delete"};
}
