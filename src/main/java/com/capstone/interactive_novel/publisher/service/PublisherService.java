package com.capstone.interactive_novel.publisher.service;

import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.publisher.domain.PublisherEntity;
import com.capstone.interactive_novel.publisher.dto.PublisherDto;
import com.capstone.interactive_novel.publisher.repository.PublisherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherService implements UserDetailsService {
    private final PublisherRepository publisherRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return publisherRepository.findByEmail(email)
                .orElseThrow(() -> new INovelException(EMAIL_NOT_FOUND));
    }

    public void register(PublisherDto.SignUp parameter) {
        Optional<PublisherEntity> optionalPublisher = publisherRepository.findByEmail(parameter.getEmail());
        if(optionalPublisher.isPresent()) {
            throw new INovelException(ALREADY_USING_EMAIL);
        }
        PublisherEntity publisher = parameter.toEntity();
        publisher.setPassword(passwordEncoder.encode(parameter.getPassword()));
        publisherRepository.save(publisher);
    }

    public String login(PublisherDto.SignIn parameter) {
        PublisherEntity publisher = publisherRepository.findByEmail(parameter.getEmail())
                .orElseThrow(() -> new INovelException(EMAIL_NOT_FOUND));

        if(!passwordEncoder.matches(parameter.getPassword(), publisher.getPassword())) {
            throw new INovelException(UNMATCHED_PASSWORD);
        }

        log.info("로그인에 성공하였습니다.");
        return publisher.getEmail();
    }
}
