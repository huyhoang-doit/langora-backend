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

        public static final class Users {
            public static final String BASE = Admin.BASE + "/users";
            public static final String ID = "/{id}";
            public static final String STATUS = "/{id}/status";
            public static final String PASSWORD = "/{id}/password";
            public static final String ROLES = "/{id}/roles";
            public static final String HISTORY = "/{id}/history";
        }

        public static final class UserProfiles {
            public static final String BASE = Admin.BASE + "/user-profiles";
            public static final String ID = "/{userId}";
            public static final String PROGRESS = "/{userId}/progress";
        }

        public static final class Languages {
            public static final String BASE = Admin.BASE + "/languages";
            public static final String ID = "/{id}";
            public static final String CODE = "/{code}";
            public static final String STATUS = "/{id}/status";
            public static final String LEVELS = "/{langId}/levels";
            public static final String WRITING_CONTENT_TYPES = "/{langId}/writing-content-types";
            public static final String WRITING_TOPICS = "/{langId}/writing-topics";
        }

        public static final class Levels {
            public static final String BASE = Admin.BASE + "/levels";
            public static final String ID = "/{id}";
        }

        public static final class WritingContentTypes {
            public static final String BASE = Admin.BASE + "/writing-content-types";
            public static final String ID = "/{id}";
        }

        public static final class WritingTopics {
            public static final String BASE = Admin.BASE + "/writing-topics";
            public static final String ID = "/{id}";
        }

        public static final class WritingExercises {
            public static final String BASE = Admin.BASE + "/writing-exercises";
            public static final String ID = "/{id}";
            public static final String STATUS = "/{id}/status";
            public static final String CONTENT = "/{id}/content";
            public static final String IMPORT = "/language/{languageId}/import";
            public static final String SENTENCES = "/{exerciseId}/sentences";
            public static final String IMPORT_SENTENCES = "/{exerciseId}/sentences/import";
        }

        public static final class WritingExerciseSentences {
            public static final String BASE = Admin.BASE + "/writing-exercise-sentences";
            public static final String ID = "/{id}";
        }
    }
}
