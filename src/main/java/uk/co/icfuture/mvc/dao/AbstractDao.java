package uk.co.icfuture.mvc.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.core.GenericTypeResolver;

import uk.co.icfuture.mvc.utils.Helper;

public abstract class AbstractDao<T> {

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<T> genericClass;

	public AbstractDao() {
		this.genericClass = Helper.uncheckedCast(GenericTypeResolver
				.resolveTypeArgument(getClass(), AbstractDao.class));
	}

	public EntityManager getEntityManager() {
		return this.entityManager;
	}

	public T persist(T entity) {
		this.entityManager.persist(entity);
		return entity;
	}

	public T update(T entity) {
		return this.entityManager.merge(entity);
	}

	public void delete(T entity) {
		this.entityManager.remove(entity);
	}

	public List<T> getList() {
		CriteriaQuery<T> query = this.entityManager.getCriteriaBuilder()
				.createQuery(this.genericClass);
		Root<T> root = query.from(this.genericClass);
		query.select(root);
		return this.entityManager.createQuery(query).getResultList();
	}

	public List<T> getList(String field, String value) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(this.genericClass);
		Root<T> root = query.from(this.genericClass);
		query.where(cb.equal(root.get(field), value));
		query.select(root);
		return this.entityManager.createQuery(query).getResultList();
	}

	public T getItem(String field, String value) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(this.genericClass);
		Root<T> root = query.from(this.genericClass);
		query.where(cb.equal(root.get(field), value));
		query.select(root);
		return this.entityManager.createQuery(query).getSingleResult();
	}

	public List<T> getFilteredList(String field, String value) {
		CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = cb.createQuery(this.genericClass);
		Root<T> root = query.from(this.genericClass);
		query.where(cb.like(root.<String> get(field),
				cb.parameter(String.class, "param")));
		TypedQuery<T> tq = this.entityManager.createQuery(query);
		tq.setParameter("param", value);
		return tq.getResultList();
	}

	public T getItemById(int id) {
		return this.entityManager.find(this.genericClass, id);
	}
}
