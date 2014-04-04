package com.fpt.springtraining.data.daoimpl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.fpt.springtraining.data.dao.IGenericDAO;

public class GenericDAOImpl<O, PK extends Serializable> implements IGenericDAO<O, PK> {
	@Autowired
	protected SessionFactory m_sessionFactory;

	private Class<O> type;

	@SuppressWarnings("unchecked")
	public GenericDAOImpl() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        type = (Class<O>) pt.getActualTypeArguments()[0];
    }

	protected Session getSession() {
		return m_sessionFactory.getCurrentSession();
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK save(O o) {
		return (PK) getSession().save(o);
	}

	@Override
	public void update(O o) {
		getSession().saveOrUpdate(o);
	}

	@Override
	public void delete(O o) {
		getSession().delete(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public O findById(PK id) {
		return (O) getSession().get(type, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<O> findAll() {
		return getSession().createCriteria(type).list();
	}

}