package com.capstone.interactive_novel.oauth.service;

import com.capstone.interactive_novel.oauth.attribute.OAuthAttribute;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final ReaderRepository readerRepository;
    // private final HttpSession httpSession;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest
                .getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        OAuthAttribute attributes = OAuthAttribute.of(userNameAttributeName, oAuth2User.getAttributes());
        ReaderEntity reader = saveOrUpdate(attributes, registrationId);

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(reader.getRole().getKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey());
    }

    private ReaderEntity saveOrUpdate(OAuthAttribute attributes, String registrationId) {
        Optional <ReaderEntity> optionalReader = readerRepository.findByEmail(attributes.getEmail());
        ReaderEntity reader;
        if(optionalReader.isEmpty()) {
            reader = attributes.toEntity(registrationId);
        }
        else {
            reader = optionalReader.get();
            reader.update(attributes.getName());
        }
        readerRepository.save(reader);
        return reader;
    }
}
