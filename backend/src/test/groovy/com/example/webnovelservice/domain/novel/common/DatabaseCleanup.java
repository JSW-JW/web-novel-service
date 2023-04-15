package com.example.webnovelservice.domain.novel.common;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.google.common.base.CaseFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Table;
import jakarta.persistence.metamodel.EntityType;
import jakarta.transaction.Transactional;

@Component
public class DatabaseCleanup implements InitializingBean {
	@PersistenceContext
	private EntityManager entityManager;

	private List<String> tableNames;

	@Override
	public void afterPropertiesSet() {
		final Set<EntityType<?>> entities = entityManager.getMetamodel().getEntities();
		tableNames = entities.stream()
			.filter(e -> isEntity(e) && hasTableAnnotation(e) && !Objects.equals(e.getName(), "User"))
			.map(e -> {
				String tableName = e.getJavaType().getAnnotation(Table.class).name();
				return tableName.isBlank() ? CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()) : tableName;
			})
			.collect(Collectors.toList());

		final List<String> entityNames = entities.stream()
			.filter(e -> isEntity(e) && !hasTableAnnotation(e))
			.map(e -> CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, e.getName()))
			.toList();

		tableNames.addAll(entityNames);
	}

	private boolean isEntity(final EntityType<?> e) {
		return null != e.getJavaType().getAnnotation(Entity.class);
	}

	private boolean hasTableAnnotation(final EntityType<?> e) {
		return null != e.getJavaType().getAnnotation(Table.class);
	}

	@Transactional
	public void execute() {
		entityManager.flush();
		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();

		for (final String tableName : tableNames) {
			entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
			entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT=1").executeUpdate();
		}

		entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS=0").executeUpdate();
	}

}
