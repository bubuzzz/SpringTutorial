package com.fpt.springtraining.data.daoimpl;

import org.springframework.stereotype.Repository;

import com.fpt.springtraining.data.dao.IRightDAO;
import com.fpt.springtraining.data.entities.AssRight;

@Repository
public class RightDAOImpl extends GenericDAOImpl<AssRight, String> implements IRightDAO {
}
