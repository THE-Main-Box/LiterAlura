package alura.LiterAlura.repository;

import alura.LiterAlura.entity.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface WriterRepository extends JpaRepository<Writer, Long> {
    @Query("SELECT w FROM Writer w WHERE w.name = :name")
    Optional<Writer> findByName(@Param("name") String name);
}
