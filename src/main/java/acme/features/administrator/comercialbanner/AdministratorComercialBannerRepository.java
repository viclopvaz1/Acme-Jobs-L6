
package acme.features.administrator.comercialbanner;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.comercialbanners.ComercialBanner;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorComercialBannerRepository extends AbstractRepository {

	@Query("select a from ComercialBanner a where a.id = ?1")
	ComercialBanner findOneById(int id);

	@Query("select a from ComercialBanner a ")
	Collection<ComercialBanner> findManyAll();

}
