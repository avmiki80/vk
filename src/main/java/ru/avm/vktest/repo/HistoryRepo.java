package ru.avm.vktest.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.avm.vktest.model.History;

public interface HistoryRepo extends JpaRepository<History, Integer> {
    @Query("select h from History h join h.groups g where " +
            ":title is null or :title = '' or lower(g.title) like lower(concat(:title, '%'))")
    Page<History> search(@Param("title") String title, Pageable pageable);
}
