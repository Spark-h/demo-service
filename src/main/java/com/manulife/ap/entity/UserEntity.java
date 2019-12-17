package com.manulife.ap.entity;

import lombok.Data;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "user_info")
@Data
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements Serializable {

    @Id
    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String id;

    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String name;

    @Column(columnDefinition = "nvarchar(50)", nullable = false)
    private String email;

}
