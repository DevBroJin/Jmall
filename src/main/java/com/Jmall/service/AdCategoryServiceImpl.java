package com.Jmall.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Jmall.domain.CategoryVO;
import com.Jmall.mapper.AdCategoryMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Log4j
@Service
public class AdCategoryServiceImpl implements AdCategoryService {

	private final AdCategoryMapper adCategoryMapper;

	@Override
	public List<CategoryVO> getFirstCategoryList() {
		return adCategoryMapper.getFirstCategoryList();
	}

	@Override
	public List<CategoryVO> getSecondCategoryList(Integer cg_parent_code) {
		return adCategoryMapper.getSecondCategoryList(cg_parent_code);
	}
	
	@Override
	public CategoryVO get(Integer cg_code) {
		return adCategoryMapper.get(cg_code);
	}
}
