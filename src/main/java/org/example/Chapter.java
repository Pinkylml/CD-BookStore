package org.example;

import javax.persistence.*;

@Entity
public class Chapter {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "book_fk")
    private Book book;
}
