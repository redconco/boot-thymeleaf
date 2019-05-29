package idu.cs.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_table")
//실제 테이블 명
//없으면 클래스명에서 대문자는 소문자로 준간 대문자는 밑줄과 소문자로 바뀜
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id; 
	// database에서 sequence number, auto increment => primary key 역할
	
	
	
	@Column(nullable=false, length=20, unique=true)
	private String userId;
	private String userPw;
	private String name;
	private String company;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	
	
}