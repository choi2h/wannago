package com.wannago.post.service.mapper;

import com.wannago.post.dto.*;
import com.wannago.post.entity.Post;
import com.wannago.post.entity.Schedule;
import com.wannago.post.entity.Tag;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    private void addSchedules(Post post, List<ScheduleRequest> scheduleRequests) {
        for (ScheduleRequest request : scheduleRequests) {
            LocalTime time = LocalTime.parse(request.getTime(), DateTimeFormatter.ofPattern("HH:mm"));

            Schedule schedule = Schedule.builder()
                    .title(request.getTitle())
                    .time(time)
                    .contents(request.getContents())
                    .locationName(request.getLocationName())
                    .lat(request.getLat())
                    .lng(request.getLng())
                    .build();

            post.addSchedule(schedule);
        }
    }

    // 엔티티 → 단건 응답 DTO 변환
    public PostResponse getPostResponse(Post post, List<String> tags, PostStatusInfo statusInfo) {
        List<ScheduleInfo> scheduleInfos = new ArrayList<>();
        post.getSchedules().forEach((schedule -> scheduleInfos.add(getScheduleInfo(schedule))));

        return PostResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .author(post.getAuthor())
                .contents(post.getContents())
                .isPublic(post.isPublic())
                .schedules(scheduleInfos)
                .statusInfo(statusInfo)
                .tags(tags)
                .build();
    }

    private ScheduleInfo getScheduleInfo(Schedule schedule) {
        return ScheduleInfo.builder()
                .title(schedule.getTitle())
                .contents(schedule.getContents())
                .locationName(schedule.getLocationName())
                .time(schedule.getTime().format(DateTimeFormatter.ofPattern(TIME_FORMAT)))
                .lat(schedule.getLat())
                .lng(schedule.getLng())
                .build();
    }

    // 엔티티 리스트 → 응답 리스트 변환
    public PostsResponse getPostsResponse(List<Post> posts, Map<Long, List<String>> tagsMap,  Map<Long, PostStatusInfo> statusMap) {
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
