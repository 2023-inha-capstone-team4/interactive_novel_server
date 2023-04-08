package com.capstone.interactive_novel.reader.service;

import com.capstone.interactive_novel.common.components.MailComponents;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.common.domain.Role;
import com.capstone.interactive_novel.reader.model.ReaderModel;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReaderService implements UserDetailsService {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailComponents mailComponents;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return readerRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 이메일입니다."));
    }

    public boolean register(ReaderModel.SignUp parameter) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(parameter.getEmail());
        if(optionalReader.isPresent()) {
            log.info("이미 사용 중인 이메일입니다.");
            return false;
        }
        String email = parameter.getEmail();
        String uuid = UUID.randomUUID().toString();

        ReaderEntity reader = parameter.toEntity();
        reader.setPassword(passwordEncoder.encode(parameter.getPassword()));
        reader.setEmailAuthKey(uuid);
        readerRepository.save(reader);

        String subject = "Interactive Novel Service Subscription Authentication Procedure";
        String message = "<h1>Interactive Novel Service Subscription Authentication Procedure</h1>" +
                         "<br><p>Please click the link below to complete the authentication.</p>" +
                         "<div><a target='_blank' href='http://localhost:8080/sign/email-auth?id=" +
                         uuid +"'> Verify </a></div>";
        mailComponents.sendMail(email, subject, message);
        return true;
    }

    public ReaderEntity login(ReaderModel.SignIn parameter) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(parameter.getEmail());
        if(optionalReader.isEmpty()) {
            log.info("일치하는 아이디가 존재하지 않습니다.");
            return null;
        }
        ReaderEntity reader = optionalReader.get();
        System.out.println(reader.getEmail());

        System.out.println(reader.getPassword());
        System.out.println(passwordEncoder.encode(reader.getPassword()));
        System.out.println(parameter.getPassword());
        if(!passwordEncoder.matches(parameter.getPassword(), reader.getPassword())) {
            log.info("비밀번호가 일치하지 않습니다.");
            return null;
        }

        if(!reader.isEmailAuthYn()) {
            log.info("이메일 인증이 완료되지 않았습니다.");
            return null;
        }
        log.info("로그인에 성공하였습니다.");
        return reader;
    }

    public boolean emailAuth(String uuid) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmailAuthKey(uuid);
        if(optionalReader.isEmpty()) {
            log.info("존재하지 않는 인증 키 입니다.");
            return false;
        }
        ReaderEntity reader = optionalReader.get();
        if(reader.isEmailAuthYn()) {
            log.info("인증이 필요하지 않은 이메일입니다.");
            return false;
        }
        reader.setEmailAuthYn(true);
        reader.setRole(Role.FREE);
        readerRepository.save(reader);
        return true;
    }

    public boolean applyAuthorService(String token) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(getEmail(token));
        if(optionalReader.isEmpty()) {
            log.info("유효하지 않은 사용자입니다.");
            return false;
        }
        ReaderEntity reader = optionalReader.get();
        if(reader.isAuthorServiceYn()) {
            log.info("이미 신청된 사용자입니다.");
            return false;
        }
        reader.setAuthorServiceYn(true);
        readerRepository.save(reader);
        return true;
    }

    public String getEmail(String token) {
        return this.parseClaims(token).getSubject();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}

