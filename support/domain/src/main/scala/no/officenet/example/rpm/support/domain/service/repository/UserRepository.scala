package no.officenet.example.rpm.support.domain.service.repository

import org.springframework.stereotype.Repository
import no.officenet.example.rpm.support.domain.model.entities.User
import no.officenet.example.rpm.support.infrastructure.jpa.{PersistenceUnits, GenericEntityRepository}

@Repository
class UserRepositoryJpa extends UserRepository with PersistenceUnits.PersistenceUnitRPM

trait UserRepository extends GenericEntityRepository[User] {

	def findByUserName(userName: String) = {
		entityManager.createQuery[User]("SELECT u FROM User u WHERE u.userName = :userName")
			.setParams("userName" -> userName)
			.findOne
	}

	def search(userSearchQuery: String) = {
		entityManager.createQuery[User]("""SELECT u FROM User u WHERE lower(u.userName) LIKE :query
										 ORDER BY u.firstName ASC, u.lastName ASC, u.userName ASC""")
			.setParams("query" -> ("%" + userSearchQuery.toLowerCase + "%"))
			.getResultList()
	}

	def findAll = {
		entityManager.createQuery[User]("SELECT u FROM User u ORDER BY u.firstName ASC, u.lastName ASC, u.userName ASC")
			.getResultList()
	}
}