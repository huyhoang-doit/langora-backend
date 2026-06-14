package com.langora.user.infrastructure.mapper;

import com.langora.user.domain.entity.UserLanguageProgress;
import com.langora.user.domain.entity.UserProfile;
import com.langora.user.dto.response.UserProfileResponse;
import com.langora.user.dto.response.UserProgressResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.10 (Homebrew)"
)
@Component
public class UserActivityMapperImpl implements UserActivityMapper {

    @Override
    public UserProfileResponse toUserProfileResponse(UserProfile userProfile) {
        if ( userProfile == null ) {
            return null;
        }

        UserProfileResponse.UserProfileResponseBuilder userProfileResponse = UserProfileResponse.builder();

        userProfileResponse.id( userProfile.getId() );
        userProfileResponse.userId( userProfile.getUserId() );
        userProfileResponse.fullName( userProfile.getFullName() );
        userProfileResponse.displayName( userProfile.getDisplayName() );
        userProfileResponse.avatarUrl( userProfile.getAvatarUrl() );
        userProfileResponse.dateOfBirth( userProfile.getDateOfBirth() );
        userProfileResponse.gender( userProfile.getGender() );
        userProfileResponse.countryCode( userProfile.getCountryCode() );
        userProfileResponse.timezone( userProfile.getTimezone() );
        userProfileResponse.bio( userProfile.getBio() );

        return userProfileResponse.build();
    }

    @Override
    public UserProgressResponse toUserProgressResponse(UserLanguageProgress userLanguageProgress) {
        if ( userLanguageProgress == null ) {
            return null;
        }

        UserProgressResponse.UserProgressResponseBuilder userProgressResponse = UserProgressResponse.builder();

        userProgressResponse.id( userLanguageProgress.getId() );
        userProgressResponse.languageId( userLanguageProgress.getLanguageId() );
        userProgressResponse.currentLevelId( userLanguageProgress.getCurrentLevelId() );
        userProgressResponse.totalLearnedWords( userLanguageProgress.getTotalLearnedWords() );
        userProgressResponse.totalMasteredWords( userLanguageProgress.getTotalMasteredWords() );
        userProgressResponse.totalLessonsCompleted( userLanguageProgress.getTotalLessonsCompleted() );
        userProgressResponse.totalStudyMinutes( userLanguageProgress.getTotalStudyMinutes() );
        userProgressResponse.currentStreak( userLanguageProgress.getCurrentStreak() );
        userProgressResponse.longestStreak( userLanguageProgress.getLongestStreak() );
        userProgressResponse.lastLearningDate( userLanguageProgress.getLastLearningDate() );

        return userProgressResponse.build();
    }
}
