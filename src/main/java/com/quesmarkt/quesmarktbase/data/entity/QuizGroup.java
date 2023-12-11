package com.quesmarkt.quesmarktbase.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.HashSet;
import java.util.Set;

/**
 * @author anercan
 */

@Getter
@Setter
@Entity
@Table(name = "quiz_group")
public class QuizGroup extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String color;
    private String imageUrl;
    private boolean active;
    private int appId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @RestResource(exported = false)
    private QuizGroup parent;

    @RestResource(exported = false)
    @JsonManagedReference
    @OneToMany(mappedBy = "parent", cascade = CascadeType.MERGE)
    private Set<QuizGroup> children = new HashSet<>();

    @Override
    public String toString() {
        return "QuizGroup{id=" + id + '}';
    }

    @Override
    public Long getId() {
        return id;
    }
}
