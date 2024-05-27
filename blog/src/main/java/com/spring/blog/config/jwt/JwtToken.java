package com.spring.blog.config.jwt;

public class JwtToken {
	private String grantType;
    private String accessToken;
    private String refreshToken;

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