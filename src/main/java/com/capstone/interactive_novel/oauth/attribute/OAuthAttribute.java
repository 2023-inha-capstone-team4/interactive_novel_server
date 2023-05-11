package com.capstone.interactive_novel.oauth.attribute;

import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.common.type.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttribute {
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String name;
    private final String email;
    private final String registrationId;

    @Builder
    public OAuthAttribute(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String registrationId) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.registrationId = registrationId;
    }

    public static OAuthAttribute of(String userNameAttributeName, Map<String, Object> attributes, String registrationId) {
        return OAuthAttribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .build();
    }

    public ReaderEntity toEntity() {
        return ReaderEntity.builder()
                .userName(name)
                .email(email)
                .interlock(registrationId)
                .emailAuthYn(true)
                .role(Role.FREE)
                .build();
    }
}
