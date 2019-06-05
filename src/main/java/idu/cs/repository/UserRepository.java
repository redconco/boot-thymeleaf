package idu.cs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import idu.cs.entity.UserEntity;

@Repository
public interface UserRepository 
	extends JpaRepository<UserEntity, Long> {
	// findById, save, delete는 선언없이도 구현 가능
	
	// 아래 메소드들은 선언해야 JPA 규칙에 의해 구현됨
	List<UserEntity> findByName(String name);
	List<UserEntity> findByCompany(String company);
	// List<UserEntity> findByCompanyOrderByIdDESC(String company);
	// util의 List
	UserEntity findByUserId(String userId);
	// find - select문, By - where, OrderBy - order by
	// ASC와 DESC를 함께 사용
	// ByUserId = where userId
	// userId 조회 
	// 반드시 findBy"찾을것"(~~);
}
