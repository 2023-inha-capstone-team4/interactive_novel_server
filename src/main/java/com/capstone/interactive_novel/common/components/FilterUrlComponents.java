package com.capstone.interactive_novel.common.components;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FilterUrlComponents {

    public Set<String> filterUrlSet() {
        List<String> urlList = new ArrayList<>(Arrays.asList("/sign/up", "/sign/in", "/sign/email-auth", "/novel/list", "/novel/score", "/novel/review/list", "/novel/comment/list", "/swagger", "/v2/api-docs", "/v3/api-docs"));
        return new HashSet<>(urlList);
    }
}
