package com.yusic.server.controller;

import com.google.gson.Gson;
import com.yusic.server.services.Youtube;
import com.yusic.server.types.ProtocoleTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@Slf4j
@RestController
public class PlayListController {

    @GetMapping("/search/list")
    public Youtube getList(
            String p){
        log.info("::::::::p {}", p);
        return new Youtube.YtBuilder()
                .setScheme("/youtube/v3/search")
                .setHttpProtocol(ProtocoleTypes.GET)
                // 완료된 동영상만, 방송중인것은 제외
                .setParameter("eventType", "completed")
                .setParameter("pageToken", p == null ? "" : p)
                // 검색어
                .setParameter("q", "bts, kpop")
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
                .build((Map m) -> {
                    System.out.println(m);
                    List<Map> l = (List)m.get("items");
                    List tmp = new ArrayList();
                    l.forEach(r -> {
                        System.out.println(r);
                        Map row = new HashMap();
                        Map snippet = (Map)r.get("snippet");
                        row.put("videoId", ((Map)r.get("id")).get("videoId"));
                        row.put("title", snippet.get("title"));
                        row.put("description", snippet.get("description") == null ? "" : snippet.get("description"));
//                        row.put("thumbnails", ((Map)((Map)r.get("thumbnails")).get("high")).get("url"));
                        tmp.add(row);
                    });
                    Map rtn = new HashMap();
                    String nextPage = (String)m.get("nextPageToken");
                    rtn.put("result", "y");
                    rtn.put("list", tmp);
                    rtn.put("nextPageToken", nextPage == null ? "" : nextPage);

                    return new Gson().toJson(rtn);
                });
    }
}
