package com.capstone.interactive_novel.publisher.service;

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

@Slf4j
@Service
@RequiredArgsConstructor
public class PublisherService implements UserDetailsService {
    private final PublisherRepository publisherRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return publisherRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
    }

    public boolean register(PublisherDto.SignUp parameter) {
        Optional<PublisherEntity> optionalPublisher = publisherRepository.findByEmail(parameter.getEmail());
        if(optionalPublisher.isPresent()) {
            log.info("이미 사용 중인 이메일입니다.");
            return false;
        }

        PublisherEntity publisher = parameter.toEntity();
        publisher.setPassword(passwordEncoder.encode(parameter.getPassword()));
        publisherRepository.save(publisher);

        return true;
    }

    public PublisherEntity login(PublisherDto.SignIn parameter) {
        Optional<PublisherEntity> optionalPublisher = publisherRepository.findByEmail(parameter.getEmail());
        if(optionalPublisher.isEmpty()) {
            log.info("일치하는 아이디가 존재하지 않습니다.");
            return null;
        }
        PublisherEntity publisher = optionalPublisher.get();

        System.out.println(publisher.getPassword());
        System.out.println(passwordEncoder.encode(publisher.getPassword()));
        System.out.println(parameter.getPassword());
        if(!passwordEncoder.matches(parameter.getPassword(), publisher.getPassword())) {
            log.info("비밀번호가 일치하지 않습니다.");
            return null;
        }

        log.info("로그인에 성공하였습니다.");
        return publisher;
    }
}
