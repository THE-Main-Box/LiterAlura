package alura.LiterAlura.entity;

import alura.LiterAlura.model.WriterRec;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "writers")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private int birthYear;
    private int deathYear;

    @ManyToMany(mappedBy = "writersList", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List<Book> booksList = new ArrayList<>();


    public Writer() {}

    public Writer(WriterRec writerRec) {
        this.name = writerRec.name();
        this.birthYear = writerRec.birth_year();
        this.deathYear = writerRec.death_year();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public int getDeathYear() {
        return deathYear;
    }

    public List<Book> getBooksList() {
        return booksList;
    }

    public void setBooksList(List<Book> booksList) {
        this.booksList = booksList;
    }

}
