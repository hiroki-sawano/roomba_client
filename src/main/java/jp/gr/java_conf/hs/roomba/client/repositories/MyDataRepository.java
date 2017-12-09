package jp.gr.java_conf.hs.roomba.client.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.gr.java_conf.hs.roomba.client.Command;

@Repository
public interface MyDataRepository extends JpaRepository<Command, Long>{
	public Command findById(long name);
	//public List<Command> findByNameLike(String name);
	public List<Command> findByIdIsNotNullOrderByIdDesc();
	//public List<Command> findByAgeGreaterThan(Integer age);
	//public List<Command> findByAgeBetween(Integer age1, Integer age2);
}
