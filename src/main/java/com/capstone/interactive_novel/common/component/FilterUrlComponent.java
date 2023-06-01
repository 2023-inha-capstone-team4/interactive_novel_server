package com.capstone.interactive_novel.common.component;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FilterUrlComponent {

    public Set<String> filterUrlSet() {
        List<String> urlList = new ArrayList<>(Arrays.asList("/sign/up", "/sign/in", "/sign/email-auth", "/novel/list", "/novel/view", "/novel/score", "/novel/review/list", "/novel/comment/list", "/swagger", "/v2/api-docs", "/v3/api-docs"));
        return new HashSet<>(urlList);
    }
}
