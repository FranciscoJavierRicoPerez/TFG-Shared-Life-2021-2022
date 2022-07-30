package net.tfg.sharedlife.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.tfg.sharedlife.model.Debt;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Repository
public interface DebtRepository extends JpaRepository<Debt, Long>{
    @Query(value = "SELECT * FROM DEBTS d WHERE d.spent_id=:id", nativeQuery = true)
    List<Debt> findDebtsBySpentId(@PathVariable("id") Long id);

    //@Query(value = "DELETE FROM DEBTS d WHERE d.id =:id", nativeQuery = true)
    //void deleteDebtById(@PathVariable("id") Long id);
}
