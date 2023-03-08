package dnick.zad5.helpyourself.repository;

import dnick.zad5.helpyourself.model.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
    List<News> findAllByDateAfter(LocalDate dateAfter);

    List<News> findAllByTitleContainingIgnoreCase(String word);
}
