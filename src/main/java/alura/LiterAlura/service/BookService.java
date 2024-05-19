package alura.LiterAlura.service;

import alura.LiterAlura.entity.Book;
import alura.LiterAlura.entity.Writer;
import alura.LiterAlura.repository.BookRepository;
import alura.LiterAlura.repository.WriterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;
    private final WriterRepository writerRepository;

    @Autowired
    public BookService(BookRepository repository, WriterRepository writerRepository){
        this.repository = repository;
        this.writerRepository = writerRepository;
    }

    public Book saveBook(Book book) {
        Optional<Book> searchedBook = repository.findByTitle(book.getTitle());

        return searchedBook.orElseGet(() -> repository.save(book));
    }

    public List<Writer> listWritersAliveInYear(int year){
        return writerRepository.findAll().stream()
                .filter(w -> year >= w.getBirthYear() && year <= w.getDeathYear())
                .toList();
    }

    public List<Book> listBooks(){
        return repository.findAll().stream()
                .toList();
    }

    public List<Writer> listWriters(){
        return writerRepository.findAll().stream().toList();
    }


    public List<Book> listBooksByLanguage(String language) {
        return repository.findAll().stream()
                .filter(b -> b.getLanguage().equalsIgnoreCase(language))
                .toList();
    }
}
