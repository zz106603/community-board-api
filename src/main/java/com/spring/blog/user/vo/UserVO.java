package com.spring.blog.user.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserVO {
	
	private long id;
	private String loginId;
	private String password;
	private String name;
	private LocalDate birthday;
	private String deleteYn;
	private LocalDateTime createDate;
	private LocalDateTime updateDate;
	private LocalDateTime deleteDate;
	private String gender;
	private String email;
	private String phone;
	private String roles;

	@Override
	public String toString() {
		return "UserVO [id=" + id + ", loginId=" + loginId + ", password=" + password + ", name=" + name + ", birthday="
				+ birthday + ", deleteYn=" + deleteYn + ", createDate=" + createDate + ", updateDate=" + updateDate
				+ ", deleteDate=" + deleteDate + ", gender=" + gender + ", email=" + email + ", phone=" + phone
				+ ", roles=" + roles + ", getId()=" + getId() + ", getLoginId()=" + getLoginId() + ", getPassword()="
				+ getPassword() + ", getName()=" + getName() + ", getBirthday()=" + getBirthday() + ", getDeleteYn()="
				+ getDeleteYn() + ", getCreateDate()=" + getCreateDate() + ", getUpdateDate()=" + getUpdateDate()
				+ ", getDeleteDate()=" + getDeleteDate() + ", getGender()=" + getGender() + ", getEmail()=" + getEmail()
				+ ", getPhone()=" + getPhone() + ", getRoles()=" + getRoles() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	

}
