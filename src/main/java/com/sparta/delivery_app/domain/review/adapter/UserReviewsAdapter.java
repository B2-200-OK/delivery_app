package com.sparta.delivery_app.domain.review.adapter;

import com.sparta.delivery_app.common.exception.errorcode.ReviewErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.ReviewDuplicatedException;
import com.sparta.delivery_app.common.globalcustomexception.ReviewNotFoundException;
import com.sparta.delivery_app.common.globalcustomexception.ReviewStatusException;
import com.sparta.delivery_app.domain.review.entity.ReviewStatus;
import com.sparta.delivery_app.domain.review.entity.UserReviews;
import com.sparta.delivery_app.domain.review.repository.UserReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserReviewsAdapter {

    private final UserReviewsRepository userReviewsRepository;

    /**
     * 리뷰 등록
     */
    public void saveReview(UserReviews userReviews) {
        userReviewsRepository.save(userReviews);
    }

    /**
     * 메뉴 id, 상태 검증
     */
    public UserReviews checkValidReviewByIdAndReviewStatus(Long reviewId) {
        UserReviews userReviews = findById(reviewId);

        if(userReviews.getReviewStatus().equals(ReviewStatus.DISABLE)) {
            throw new ReviewStatusException(ReviewErrorCode.DELETED_REVIEW);
        }

        return userReviews;
    }

    public void validateManagerReviewExistsByReviewId(Long reviewId) {
        if (!userReviewsRepository.findManagerReviewIdById(reviewId).isEmpty()) {
            throw new ReviewDuplicatedException(ReviewErrorCode.REVIEW_ALREADY_REGISTERED_ERROR);
        }
    }

    public Long validateManagerReviewDoesNotExistByReviewId(Long reviewId) {
        return userReviewsRepository.findManagerReviewIdById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }

    /**
     * 리뷰 Id 검증
     */
    private UserReviews findById(Long reviewId) {
        return userReviewsRepository.findById(reviewId).orElseThrow(() ->
                new ReviewNotFoundException(ReviewErrorCode.INVALID_REVIEW));
    }
}