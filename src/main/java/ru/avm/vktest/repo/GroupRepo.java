package ru.avm.vktest.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.avm.vktest.model.Group;
import ru.avm.vktest.model.History;

import java.util.Collection;
import java.util.Set;

public interface GroupRepo extends JpaRepository<Group, Integer> {
    @Query("select g from Group g where g.vkId in :vkIds")
    Set<Group> findByVkIds(@Param("vkIds") Collection<Integer> vkIds);

    @Query("select g from Group g where " +
            ":title is null or :title = '' or lower(g.title) like lower(concat(:title, '%'))")
    Page<Group> search(@Param("title") String title, Pageable pageable);
}
