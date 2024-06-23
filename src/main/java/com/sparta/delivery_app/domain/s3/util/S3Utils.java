package com.sparta.delivery_app.domain.s3.util;

import com.sparta.delivery_app.common.exception.errorcode.S3ErrorCode;
import com.sparta.delivery_app.common.globalcustomexception.S3Exception;
import lombok.Builder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

@Component
public class S3Utils {

    private static final String URL_PREFIX = "Delivery";
    // 메뉴 이미지 저장 경로 store/{storeId}/menu/{menuId}
    private static final String STORE_DIR = "store";
    private static final String MENU_IMAGE_DIR = "menu";

    // 리뷰 이미지 저장 경로 user/{userId}/review/{reviewId}
    private static final String USER_DIR = "user";
    private static final String REVIEW_IMAGE_DIR = "review";
    private static final String PROFILE_IMAGE_DIR = "profile";

    /**
     * 파일 존재 여부 검사
     */
    public static boolean isFileExists(MultipartFile multipartFile) {
        return multipartFile != null && !multipartFile.isEmpty();
    }

    /**
     * 파일 확장자 검사
     */
    public static void validateImageExtension(String fileName) {
        List<String> validExtensionList = Arrays.asList("jpg", "jpeg", "png","webp");

        int extensionIndex = fileName.lastIndexOf(".");

        String extension = fileName.substring(extensionIndex + 1).toLowerCase();

        if(!validExtensionList.contains(extension)) {
            throw new S3Exception(S3ErrorCode.INVALID_EXTENSION);
        }
    }

    /**
     * S3 이미지 경로 추출
     */
    public static String extractImagePath(String imagePath) {
        int startIndex = imagePath.indexOf(URL_PREFIX);
        if (startIndex == -1) {
            throw new S3Exception(S3ErrorCode.INVALID_IMAGE_URL);
        }
        return imagePath.substring(startIndex);
    }

    public static String createMenuImageDir(Long storeId, Long menuId) {
        return URL_PREFIX + "/"
                + STORE_DIR + "/"
                + storeId + "/"
                + MENU_IMAGE_DIR + "/"
                + menuId + "/";
    }

    public static String createReviewImageDir(Long userId, Long reviewId) {
        return URL_PREFIX + "/"
                + USER_DIR + "/"
                + userId + "/"
                + REVIEW_IMAGE_DIR + "/"
                + reviewId + "/";
    }
}