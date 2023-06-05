package ru.avm.vktest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "vk_history", uniqueConstraints = @UniqueConstraint(columnNames = {"request_date"}, name = "vk_history_uniq"))
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class History extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_seq")
    @SequenceGenerator(name = "history_seq", initialValue = 2000, allocationSize = 1)
    private Integer id;

    @Column(name = "request_param", nullable = false)
    private String requestParam;

    @Column(name = "request_date", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime requestDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    @JoinTable(
            name = "history_group",
            joinColumns = @JoinColumn(name = "history_id", foreignKey = @ForeignKey(name = "fk_history")),
            inverseJoinColumns = @JoinColumn(name = "group_id", foreignKey = @ForeignKey(name = "fk_group"))
    )
    private Set<Group> groups;

    public void addGroup(Group group){
        groups.add(group);
        group.getHistories().add(this);
    }

    public void removeGroup(Group group){
        groups.remove(group);
        group.getHistories().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof History)) return false;
        History history = (History) o;
        return Objects.equals(getRequestDate(), history.getRequestDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRequestDate());
    }
}
