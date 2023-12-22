package com.sparta.plusproject.global;

import com.sparta.plusproject.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final PostRepository postRepository;

    @Scheduled(cron = "0 0 0 1 */3 *" )
    public void deleteOldData() {
        postRepository.deleteAll();
    }
}
