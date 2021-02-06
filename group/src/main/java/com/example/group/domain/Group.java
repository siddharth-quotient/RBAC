package com.example.group.domain;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "GROUP_TABLE")
@EntityListeners(AuditingEntityListener.class)
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @Column(updatable = false) //false (by default)
    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;


    @Column(unique = true)
    private String groupName;

    private String groupDescription;
}
