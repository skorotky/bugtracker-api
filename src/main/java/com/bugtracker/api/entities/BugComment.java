package com.bugtracker.api.entities;

import com.bugtracker.api.entities.audit.AuditMetadata;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "bug_comment")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class BugComment extends AuditMetadata implements Serializable {

    @Serial
    private static final long serialVersionUID = 874032472355232L;

    private Long id;
    private String body;
    private User author;
    private Bug bug;

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    public Long getId() {
        return id;
    }

    @Column(name = "body", nullable = false)
    public String getBody() {
        return body;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", updatable = false)
    public User getAuthor() {
        return author;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bug_id", updatable = false)
    public Bug getBug() {
        return bug;
    }

}
