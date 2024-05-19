package alura.LiterAlura.entity;

import alura.LiterAlura.model.BookRec;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany(mappedBy = "booksList",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Writer> writersList = new ArrayList<>();

    private String language;
    private int selledCopies;

    public Book() {}
    public Book(BookRec bookRec) {
        this.title = bookRec.title();
        this.language = bookRec.languages().getFirst();
        this.selledCopies = bookRec.download_count();
    }

    public void setWritersList(List<Writer> writersList) {
        this.writersList = writersList;
    }

    public void addWriterList(Writer writer){
        this.writersList.add(writer);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Writer> getWritersList() {
        return writersList;
    }

    public String getLanguage() {
        return language;
    }

    public int getSelledCopies() {
        return selledCopies;
    }

    @Override
    public String toString() {
        return"title='" + title + '\'' +
                ", writersList=" + writersList +
                ", language='" + language + '\'' +
                ", selledCopies=" + selledCopies;
    }
}
