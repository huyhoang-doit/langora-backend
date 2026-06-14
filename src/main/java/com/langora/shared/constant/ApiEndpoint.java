package com.langora.shared.constant;

public final class ApiEndpoint {

    private ApiEndpoint() {}

    public static final String API_V1 = "/api/v1";

    public static final class Admin {
        public static final String BASE = API_V1 + "/admin";

        public static final class Auth {
            public static final String BASE = Admin.BASE + "/auth";
            public static final String LOGIN = "/login";
            public static final String ME = "/me";
        }

        public static final class Roles {
            public static final String BASE = Admin.BASE + "/roles";
            public static final String ID = "/{id}";
        }

        public static final class Permissions {
            public static final String BASE = Admin.BASE + "/permissions";
        }
    }
}
