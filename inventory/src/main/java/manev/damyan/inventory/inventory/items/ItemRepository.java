package manev.damyan.inventory.inventory.items;

import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long>, PagingAndSortingRepository<Item, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM Item c WHERE c.id = ?1")
    Optional<Item> findByIdWithPessimisticLock(Long id);

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    @QueryHints({ @QueryHint(name = "jakarta.persistence.lock.timeout", value = "3000") })
    @Transactional
    Optional<Item> findById(Long id);

    @Override
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    <S extends Item> S save(S entity);

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    <S extends Item> Page<S> findAll(Example<S> example, Pageable pageable);

    List<Item> findByName(String name);

    @Query("SELECT i FROM Item i where UPPER(i.name) = UPPER(:nameCaseInsensitive)")
    Optional<List<Item>> findByNameInsensitive(@Param("nameCaseInsensitive") String nameInsensitive);

    // the same as the one above, but using Spring generation option
    Optional<List<Item>> findByNameIgnoreCase(String name);

    @Transactional
    List<Item> deleteByName(String name);

    List<ItemShortProjection> findByNameAndId(String name, Long id);
}