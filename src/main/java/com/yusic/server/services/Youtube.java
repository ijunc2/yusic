package com.yusic.server.services;

import com.google.gson.Gson;
import com.yusic.server.services.internal.ActionCunsumer;
import com.yusic.server.services.internal.ActionCunsumerError;
import com.yusic.server.types.ProtocoleTypes;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;


/**
 * This is a function which is to use Youtube APIs. This Class was structured on Builder Pattern.
 * Builer Class is YtBuilder which has some of setting method.
 * SCHEME, PARAMETER, POST PARAMETERS, A TYPE OF CONNECTION(GET, POST... defualt is GET),
 * SNIPPET, Handling function of error,
 */
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
            this.options.getAllParameter().forEach((k, v) -> {
                PARAM.append("&" + k).append("=" + v);
            });


            String result = connection(PARAM);

            return ac.execute(new Gson().fromJson(result, Map.class));
        }).thenAccept(s -> this.setResult(s))
        .exceptionally(e -> {
            log.error(e.getCause().toString());
            this.setErrorResult(this.options.getActionCunsumerError().error(e));
            return null;
        });
    }

    private String connection(StringBuilder PARAM) throws RuntimeException{
        String result = "";
        switch (this.options.getProtocole()){
            case GET:
                return rt.getForObject(URL + this.options.getScheme() + PARAM, String.class);
            case POST:
                return rt.postForObject(
                        URL + this.options.getScheme() + PARAM,
                        this.options.postParam, String.class);
        }

        return result;
    }

    public static class YtBuilder{
        private String SCHEME;

        private String snippet;

        private ActionCunsumerError ae;

        private Map param;

        private Map postParam;

        private ProtocoleTypes pt = ProtocoleTypes.GET;

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
            if(key == null || "".equals(key) || "undefined".equals(value) ||
                    value == null || "".equals(value) ){
                return this;
            }
            if(param == null) param = new HashMap();
            param.put(key, value);

            return this;
        }

        protected Map getAllParameter(){
            return this.param;
        }

        public YtBuilder setPostParameter(String key, String value){
            if(key == null || "".equals(key) ||
                    value == null || "".equals(value) ){
                return this;
            }

            if(postParam == null) postParam = new HashMap();

            postParam.put(key, value);

            return this;
        }

        protected Map getAllPostParameter(){
            return this.postParam;
        }

        public YtBuilder setHttpProtocol(ProtocoleTypes pt){
            this.pt = pt;
            return this;
        }

        protected ProtocoleTypes getProtocole(){
            return this.pt;
        }

        public Youtube build(ActionCunsumer ac){
            Youtube y = new Youtube(this, ac);
            return y;
        }
    }
}
