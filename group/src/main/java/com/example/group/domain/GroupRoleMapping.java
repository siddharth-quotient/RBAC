package com.example.group.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "GROUP_ROLE_MAPPING_TABLE",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"group_id", "role_id"})}
)
public class GroupRoleMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupRoleId;

    @Column(name="group_id")
    private Long groupId;
    @Column(name="role_id")
    private Long roleId;
}
