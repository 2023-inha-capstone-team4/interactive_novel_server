package com.capstone.interactive_novel.reader.service;

import com.capstone.interactive_novel.common.components.MailComponents;
import com.capstone.interactive_novel.common.exception.INovelException;
import com.capstone.interactive_novel.common.service.S3Service;
import com.capstone.interactive_novel.reader.domain.ReaderEntity;
import com.capstone.interactive_novel.common.type.Role;
import com.capstone.interactive_novel.reader.dto.ReaderDto;
import com.capstone.interactive_novel.reader.repository.ReaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;

import static com.capstone.interactive_novel.common.exception.ErrorCode.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReaderService implements UserDetailsService {
    @Value("${spring.jwt.secret}")
    private String secretKey;
    private final ReaderRepository readerRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailComponents mailComponents;
    private final S3Service s3Service;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return readerRepository.findByEmail(email)
                .orElseThrow(() -> new INovelException(EMAIL_NOT_FOUND));
    }

    public void register(ReaderDto.SignUp parameter) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmail(parameter.getEmail());
        if(optionalReader.isPresent()) {
            throw new INovelException(ALREADY_USING_EMAIL);
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
    }

    public String login(ReaderDto.SignIn parameter) {
        ReaderEntity reader = readerRepository.findByEmail(parameter.getEmail())
                .orElseThrow(() -> new INovelException(EMAIL_NOT_FOUND));

        if(!passwordEncoder.matches(parameter.getPassword(), reader.getPassword())) {
            throw new INovelException(UNMATCHED_PASSWORD);
        }
        if(!reader.isEmailAuthYn()) {
            throw new INovelException(UNVERIFIED_EMAIL);
        }
        log.info("로그인에 성공하였습니다.");
        return reader.getEmail();
    }

    public boolean emailAuth(String uuid) {
        Optional<ReaderEntity> optionalReader = readerRepository.findByEmailAuthKey(uuid);
        if(optionalReader.isEmpty()) {
            throw new INovelException(AUTH_KEY_NOT_FOUND);
        }
        ReaderEntity reader = optionalReader.get();
        if(reader.isEmailAuthYn()) {
            throw new INovelException(ALREADY_VERIFIED_EMAIL);
        }
        reader.setEmailAuthYn(true);
        reader.setRole(Role.FREE);
        readerRepository.save(reader);
        return true;
    }

    public String applyAuthorService(ReaderEntity reader) {
        if(reader.isAuthorServiceYn()) {
            throw new INovelException(ALREADY_VERIFIED_USER);
        }
        reader.setAuthorServiceYn(true);
        readerRepository.save(reader);
        return reader.getId() + ": " + reader.isAuthorServiceYn();
    }

    public ReaderDto.profileImg modifyProfileImg(ReaderEntity reader, MultipartFile file, String domain) {
        String imageUrl = s3Service.uploadFile(file, "profile", String.valueOf(reader.getId()));
        reader.setProfileImgUrl(imageUrl);
        readerRepository.save(reader);

        return ReaderDto.profileImg.of(reader.getEmail(), reader.getUsername(), domain, imageUrl);
    }
}

