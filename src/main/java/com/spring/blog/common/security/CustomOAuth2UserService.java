package com.spring.blog.common.security;

import com.spring.blog.common.exception.BaseException;
import com.spring.blog.user.mapper.UserMapper;
import com.spring.blog.user.vo.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 추가적인 사용자 정보 처리 (DB 저장 등)

        // 사용자 정보를 가져옴
        String email = oAuth2User.getAttribute("email");
        String name = oAuth2User.getAttribute("name");

        logger.info(email);
        logger.info(name);

        try {
            UserVO userOne = userMapper.findById(email);

            if(userOne == null){
                UserVO user = new UserVO();
                user.setLoginId(email);
                user.setPassword(email);
                user.setEmail(email);
                user.setName(name);

                try {
                    /*
                     * 검증하려면 passwordEncoder.matches(들어온 변수, DB에서 가져온 값))
                     */
                    user.setPassword(null);
                    user.setDeleteYn("N");
                    LocalDateTime now = LocalDateTime.now();
                    user.setCreateDate(now);
                    user.setUpdateDate(now);
                    user.setRoles("ROLE_USER");

                    userMapper.createUser(user);
                    userOne = user;
                }catch (IllegalArgumentException e) {
                    throw new BaseException(HttpStatus.BAD_REQUEST, "Password encoding failed: " + e.getMessage());
                }catch (DataAccessException e) {
                    throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Database operation failed: " + e.getMessage());
                }catch (BaseException e) {
                    throw e;
                }catch (Exception e) {
                    throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
                }
            }else{
                logger.info("이미 가입이 완료된 사용자입니다.");
            }

            // CustomOAuth2User로 권한 부여 (Spring Security가 권한을 인식할 수 있도록)
            return new CustomOAuth2User(oAuth2User, userOne.getRoles());

        }catch (DataAccessException e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "Database operation failed: " + e.getMessage());
        }catch(BaseException e) {
            throw e;
        }catch (Exception e) {
            throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred: " + e.getMessage());
        }

    }

}
