package com.spring.blog.config.jwt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtToken {
	private String grantType;
    private String accessToken;
    private String refreshToken;

    public JwtToken() {
        // 기본 생성자 필요
    }

    @JsonCreator
    public JwtToken(@JsonProperty("grantType") String grantType,
                    @JsonProperty("accessToken") String accessToken,
                    @JsonProperty("refreshToken") String refreshToken) {
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
    
	private JwtToken(Builder builder) {
    	this.grantType = builder.grantType;
        this.accessToken = builder.accessToken;
        this.refreshToken = builder.refreshToken;
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

	public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
    	private String grantType;
        private String accessToken;
        private String refreshToken;

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

        public JwtToken build() {
            return new JwtToken(this);
        }
    }
}