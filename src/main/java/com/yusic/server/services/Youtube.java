package com.yusic.server.services;

import com.google.gson.Gson;
import com.yusic.server.services.internal.ActionCunsumer;
import com.yusic.server.services.internal.ActionCunsumerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Slf4j
public class Youtube extends DeferredResult{

    private YtBuilder options;

    private Youtube(){}

    private RestTemplate rt ;

    private static final String URL = "https://www.googleapis.com";
    
    private static final String KEY = "AIzaSyA9TJr-tUOdW1ldsDefrZeRKu8hN6QX4j8";

    Youtube(YtBuilder options, ActionCunsumer ac){
        this.options = options;
        rt = new RestTemplate();
        this.execute(ac);
    }

    private void execute(ActionCunsumer ac){
        CompletableFuture.supplyAsync(() -> {
            StringBuilder PARAM = new StringBuilder();
            String snippet = this.options.getSnippet() == null ? "snippet" : this.options.getSnippet();
            PARAM.append("?part=" + snippet)
                .append("&key=" + KEY);
            this.options.getParameter().forEach((k, v) -> {
                PARAM.append("&" + k).append("=" + v);
            });

            log.info(URL + this.options.getScheme() + PARAM);

            String result = rt.getForObject(URL + this.options.getScheme() + PARAM, String.class);

            log.info(result);

            return ac.execute(new Gson().fromJson(result, Map.class));
        }).thenAccept(s -> this.setResult(s))
        .exceptionally(e -> {
            log.error(e.getCause().toString());
            this.setErrorResult(this.options.getActionCunsumerError().error(e));
            return null;
        });
    }

    public static class YtBuilder{
        private String SCHEME;

        private String snippet;

        private ActionCunsumerError ae;

        private Map param;

        public YtBuilder setSnippet(String snippet){
            this.snippet = snippet;
            return this;
        }

        public String getSnippet() {
            return this.snippet;
        }

        public YtBuilder setScheme(String SCHEME){
            this.SCHEME = SCHEME;
            return this;
        }

        public String getScheme() {
            return this.SCHEME;
        }

        public YtBuilder exceptional(ActionCunsumerError ae){
            this.ae = ae;
            return this;
        }

        protected ActionCunsumerError getActionCunsumerError(){
            return this.ae;
        }

        public YtBuilder setParameter(String key, String value){
            if(key == null || "".equals(key) ||
                    key == null || "".equals(key) ){
                return this;
            }
            if(param == null) param = new HashMap();
            param.put(key, value);

            return this;
        }

        protected Map getParameter(){
            return this.param;
        }

        public Youtube build(ActionCunsumer ac){
            Youtube y = new Youtube(this, ac);
            return y;
        }
    }
}
