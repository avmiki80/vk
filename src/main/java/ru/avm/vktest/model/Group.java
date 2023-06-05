package ru.avm.vktest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Table(name = "vk_group", uniqueConstraints = @UniqueConstraint(columnNames = {"vk_id"}, name = "vk_group_uniq"))
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
public class Group extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "group_seq")
    @SequenceGenerator(name = "group_seq", initialValue = 2000, allocationSize = 1)
    private Integer id;

    @Column(name = "vk_id", nullable = false)
    private Integer vkId;
    @Column(nullable = false)
    private String title;

    @ManyToMany(mappedBy = "groups")
    private Set<History> histories = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Group)) return false;

        Group group = (Group) o;

        return getVkId().equals(group.getVkId());
    }

    @Override
    public int hashCode() {
        return getVkId().hashCode();
    }
}
