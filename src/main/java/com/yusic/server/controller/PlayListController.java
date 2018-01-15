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
    public Youtube getChannel(
            String p){
        return new Youtube.YtBuilder()
                .setScheme("/youtube/v3/playlistItems")
                .setHttpProtocol(ProtocoleTypes.GET)
                .setSnippet("snippet")
                .setParameter("playlistId", "PLvxl7qPWdH7ZpMgAbszncGPEGeEna9TTp")
                .setParameter("pageToken", p)
                .exceptional(e -> "error !!!!\\n")
                .build((Map m) -> {
                    List<Map> l = (List)m.get("items");
                    List tmp = new ArrayList();
                    l.forEach(r -> {
                        Map row = new HashMap();
                        Map snippet = (Map)r.get("snippet");
                        row.put("videoId", ((Map)snippet.get("resourceId")).get("videoId"));
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
