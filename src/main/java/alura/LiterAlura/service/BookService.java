package alura.LiterAlura.service;

import alura.LiterAlura.entity.Book;
import alura.LiterAlura.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository repository;

    @Autowired
    public BookService(BookRepository repository){
        this.repository = repository;
    }

    public Book saveBook(Book book){
        Optional<Book> searchedBook = repository.findByTitle(book.getTitle());

        if (searchedBook.isEmpty()) {
            return repository.save(book);
        } else {
            return repository.findByTitle(book.getTitle()).get();
        }
    }

}
