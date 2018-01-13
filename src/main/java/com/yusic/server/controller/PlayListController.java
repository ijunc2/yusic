package com.yusic.server.controller;

import com.yusic.server.services.Youtube;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@Slf4j
@RestController
public class PlayListController {

    @GetMapping("/search/list")
    public Youtube getList(){
        return new Youtube.YtBuilder()
                .setScheme("/youtube/v3/search")
                // 완료된 동영상만, 방송중인것은 제외
                .setParameter("eventType", "completed")
                // 검색어
                .setParameter("q", "제이레빗")
                .setParameter("videoDuration", "short")
                // 라이센스 (현재 유튜브 라이센스)
//                .setParameter("videoLicense", "youtube")
                // 어디서든 재생가능한지.
                .setParameter("videoEmbeddable", "true")
                // 비디오 타입만
                .setParameter("type", "video")
                // 영상 화질
                .setParameter("videoDefinition", "high")
//                .setParameter("videoType", "movie")
                .exceptional(e -> "error !!!!\\n")
                .build(m -> {
                    System.out.println(m);
                    log.info("sasdlfkjasldkfjlaskdjf");
                    return "asdfasdf";
                });
    }
}
