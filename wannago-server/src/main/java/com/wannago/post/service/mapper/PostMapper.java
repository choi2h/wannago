package com.wannago.post.service.mapper;

import com.wannago.post.dto.*;
import com.wannago.post.entity.DailySchedule;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.TimeSchedule;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PostMapper {
    private static final String TIME_FORMAT = "HH:mm";

    // 요청 → 엔티티 변환
    public Post getPost(PostRequest postRequest, boolean isPublic) {
        Post post =  Post.builder()
                .title(postRequest.getTitle())
                .author(postRequest.getAuthor())
                .contents(postRequest.getContents())
                .isPublic(isPublic)
                .build();

        addSchedules(post, postRequest.getSchedules());
        return post;
    }

    public void addSchedules(Post post, List<DailyScheduleInfo> scheduleRequests) {
        for (DailyScheduleInfo request : scheduleRequests) {
            DailySchedule dailySchedule = DailySchedule.builder()
                    .day(request.getDay())
                    .build();

            request.getTimeSchedules().forEach(timeSchedule -> {
                dailySchedule.addTimeSchedule(toTimeSchedule(timeSchedule));
            });

            post.addSchedule(dailySchedule);
        }
    }

    private TimeSchedule toTimeSchedule(TimeScheduleInfo timeScheduleInfo) {
        LocalTime time = LocalTime.parse(timeScheduleInfo.getTime(), DateTimeFormatter.ofPattern("HH:mm"));
        return TimeSchedule.builder()
                    .title(timeScheduleInfo.getTitle())
                    .time(time)
                    .contents(timeScheduleInfo.getContents())
                    .locationName(timeScheduleInfo.getLocationName())
                    .lat(timeScheduleInfo.getLat())
                    .lng(timeScheduleInfo.getLng())
                    .build();
    }

    // 엔티티 → 단건 응답 DTO 변환
    public PostResponse getPostResponse(Post post, List<String> tags, PostStatusInfo statusInfo) {
        List<DailyScheduleInfo> scheduleInfos = new ArrayList<>();
        post.getDailySchedules().forEach(dailySchedule ->
            scheduleInfos.add(toDailyScheduleInfo(dailySchedule)));
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .contents(post.getContents())
                .isPublic(post.isPublic())
                .createdAt(post.getCreatedDate())
                .schedules(scheduleInfos)
                .statusInfo(statusInfo)
                .tags(tags)
                .build();
    }

    private DailyScheduleInfo toDailyScheduleInfo(DailySchedule dailySchedule) {
        DailyScheduleInfo dailyScheduleInfo = DailyScheduleInfo.builder()
                .day(dailySchedule.getDay()).build();

        dailySchedule.getTimeSchedules().forEach(timeSchedule -> {
            dailyScheduleInfo.addTimeSchedule(toTimeScheduleInfo(timeSchedule));
        });

        return dailyScheduleInfo;
    }

    private TimeScheduleInfo toTimeScheduleInfo(TimeSchedule timeSchedule) {
        String time = timeSchedule.getTime().format(DateTimeFormatter.ofPattern("HH:mm"));
        return TimeScheduleInfo.builder()
                .title(timeSchedule.getTitle())
                .contents(timeSchedule.getContents())
                .locationName(timeSchedule.getLocationName())
                .time(time)
                .lat(timeSchedule.getLat())
                .lng(timeSchedule.getLng())
                .build();
    }

    // 마이페이지용 - 단순 Post → PostResponse 변환 (제목 + 작성일)
    public PostResponse toPostSimpleResponse(Post post) {
        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .createdAt(post.getCreatedDate())
                .author(post.getAuthor())
                .contents(post.getContents())
                .isPublic(post.isPublic())
                .likeCount(0)
                .liked(false)
                .build();
    }

    public List<PostResponse> toPostSimpleResponseList(List<Post> posts) {
        return posts.stream()
                .map(this::toPostSimpleResponse)
                .collect(Collectors.toList());
    }

    // 엔티티 리스트 → 응답 리스트 변환
    public PostsResponse getPostsResponse
        (List<Post> posts, Map<Long, List<String>> tagsMap, Map<Long, PostStatusInfo> statusMap) {
        PostsResponse response = new PostsResponse();

        for (Post post : posts) {
            PostStatusInfo status = statusMap.getOrDefault(post.getId(),
                    new PostStatusInfo(0, false, false));
            List<String> tags = tagsMap.get(post.getId());
            PostResponse postResponse = getPostResponse(post, tags, status);
            response.addPost(postResponse);
        }

        return response;
    }
}
