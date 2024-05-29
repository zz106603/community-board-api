package com.spring.blog.config.jwt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {
	private String grantType;
    private String accessToken;
    private String refreshToken;
    private String loginId;
    private String userName;

    public JwtToken() {
        // 기본 생성자 필요
    }

    @JsonCreator
    public JwtToken(@JsonProperty("grantType") String grantType,
                    @JsonProperty("accessToken") String accessToken,
                    @JsonProperty("refreshToken") String refreshToken,
                    @JsonProperty("loginId") String loginId,
                    @JsonProperty("userName") String userName) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.loginId = loginId;
        this.userName = userName;
    }
    
	private JwtToken(Builder builder) {
    	this.grantType = builder.grantType;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
        this.loginId = builder.loginId;
        this.userName = builder.userName;
    }

    public String getAccessToken() {
		return accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public String getGrantType() {
		return grantType;
	}

	public String getLoginId() {
		return loginId;
	}

	public String getUserName() {
		return userName;
	}

	public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
    	private String grantType;
        private String accessToken;
        private String refreshToken;
        private String loginId;
        private String userName;

        public Builder grantType(String grantType) {
            this.grantType = grantType;
            return this;
        }

        public Builder accessToken(String accessToken) {
            this.accessToken = accessToken;
            return this;
        }

        public Builder refreshToken(String refreshToken) {
            this.refreshToken = refreshToken;
            return this;
        }
        
        public Builder loginId(String loginId) {
            this.loginId = loginId;
            return this;
        }
        
        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }
        
        

        public JwtToken build() {
            return new JwtToken(this);
        }
    }
}