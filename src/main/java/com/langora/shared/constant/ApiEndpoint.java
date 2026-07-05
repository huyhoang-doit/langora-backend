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

    public static final class Client {

        public static final class Auth {
            public static final String BASE = API_V1 + "/auth";
            public static final String LOGIN = "/login";
            public static final String REGISTER = "/register";
            public static final String REFRESH_TOKEN = "/refresh-token";
            public static final String LOGOUT = "/logout";
        }

        public static final class EmailVerifications {
            public static final String BASE = API_V1 + "/email-verifications";
        }

        public static final class PasswordResets {
            public static final String BASE = API_V1 + "/password-resets";
            public static final String REQUEST = "/request";
            public static final String RESET = "/reset";
        }

        public static final class LoginHistories {
            public static final String BASE = API_V1 + "/login-histories";
            public static final String ME = "/me";
        }

        public static final class UserProfiles {
            public static final String BASE = API_V1 + "/user-profiles";
            public static final String ME = "/me";
            public static final String AVATAR = "/me/avatar";
        }

        public static final class UserPreferences {
            public static final String BASE = API_V1 + "/user-preferences";
            public static final String ME = "/me";
        }

        public static final class UserDevices {
            public static final String BASE = API_V1 + "/user-devices";
            public static final String ID = "/{id}";
        }

        public static final class UserLearningProfiles {
            public static final String BASE = API_V1 + "/user-learning-profiles";
            public static final String ME = "/me";
        }

        public static final class UserLearningGoals {
            public static final String BASE = API_V1 + "/user-learning-goals";
            public static final String ME = "/me";
        }

        public static final class Languages {
            public static final String BASE = API_V1 + "/languages";
            public static final String ID = "/{id}";
            public static final String LEVELS = "/{langId}/levels";
            public static final String WRITING_CONTENT_TYPES = "/{langId}/writing-content-types";
            public static final String WRITING_TOPICS = "/{langId}/writing-topics";
        }

        public static final class WritingExercises {
            public static final String BASE = API_V1 + "/languages/{languageId}/writing-exercises";
            public static final String ID = "/{id}";
        }

        public static final class WritingSessions {
            public static final String BASE = API_V1 + "/writing-sessions";
            public static final String ID = "/{id}";
            public static final String SUBMIT = "/{id}/submit";
            public static final String SENTENCE_ANSWERS = "/{id}/sentence-answers";
            public static final String AI_FEEDBACKS = "/{id}/ai-feedbacks";
        }

        public static final class WritingAchievements {
            public static final String BASE = API_V1 + "/writing-achievements";
            public static final String ME = "/me";
        }
    }
}
