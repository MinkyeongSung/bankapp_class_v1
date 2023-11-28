package com.tenco.bankapp.repository.interfaces;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.tenco.bankapp.repository.entity.History;

@Mapper
public interface HistoryRepository {

	// 코드 수정 함
	public int insert(History history);

	public int updateById(History history);

	public int deleteById(Integer id);

	public List<History> findAll();

	public List<History> findByIdAndDynamicType(@Param("type") String type, @Param("accountId") Integer accountId);

}