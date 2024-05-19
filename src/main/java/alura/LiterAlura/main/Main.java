package alura.LiterAlura.main;

import alura.LiterAlura.entity.Book;
import alura.LiterAlura.entity.Writer;
import alura.LiterAlura.model.ApiResponse;
import alura.LiterAlura.model.BookRec;
import alura.LiterAlura.repository.BookRepository;
import alura.LiterAlura.service.BookService;
import alura.LiterAlura.service.ConnectToGutendexApi;
import alura.LiterAlura.service.ConvertToRec;
import alura.LiterAlura.service.URLGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class Main {

    private Scanner reader = new Scanner(System.in);
    private ConnectToGutendexApi connectToGutendexApi = new ConnectToGutendexApi();
    private ConvertToRec conversor = new ConvertToRec();
    private URLGenerator urlGenerator = new URLGenerator();

    private Writer writer;
    private Book book;

    private final BookService bookService;

    public Main(BookService bookService){
        this.bookService = bookService;
    }

    public void showMenu() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("***********Escolha uma Opção************");
            System.out.println("""
                    1 - Registrar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    
                    0 - SAIR
                    """);
            System.out.println("****************************************");

            choice = reader.nextInt();
            reader.nextLine();

            switch (choice) {
                case 1:
                    this.setBook();
                    break;
                case 2:
                    this.listRegistredBooks();
                    break;
                case 3:
                    this.listRegistredWriters();
                    break;
                case 4:
                    this.listAliveWriters();
                    break;
                case 5:
                    this.listBooksByLanguage();
                    break;
                case 0:
                    System.out.println("saindo...");
                    break;
                default:
                    System.out.println("Escolha uma opção VÁLIDA");
                    break;
            }

        }

    }

    private void listBooksByLanguage() {
        System.out.println("Qual o indioma do livro em questão");
        String userResponse = reader.nextLine();

        List<Book> bookList = bookService.listBooksByLanguage(userResponse);

        if(bookList.isEmpty()){
            System.out.println("Não há nenhum livro com esta lingua");
        }else{
            bookList.forEach(this::showBookData);
        }
    }

    private void listAliveWriters() {
        System.out.println("Em que ano deseja ohar");
        int userResponse = reader.nextInt();
        reader.nextLine();

        List<Writer> liveWriters = bookService.listWritersAliveInYear(userResponse);

        if(liveWriters.isEmpty()){
            System.out.println("Não existe nenhum escritor cadastrado vivo nessa época");
        } else{
            liveWriters.forEach(this::showWriterData);
        }
    }

    private void listRegistredWriters() {
        this.bookService.listWriters().forEach(this::showWriterData);
    }

    private void listRegistredBooks() {
        this.bookService.listBooks().forEach(this::showBookData);
    }

    //Realiza uma pesquisa web com o nome de um livro e seta em variáveis globais o livro e seus escritores e os relaciona
    private void setBook() {
        System.out.println("Qual o titulo do livro");
        String userResponse = reader.nextLine();

        userResponse = urlGenerator.generateSearchUrlString(userResponse);

        String json = connectToGutendexApi.connect(userResponse);

        try {
            ApiResponse apiResponse = conversor.convert(json, ApiResponse.class);

            BookRec bookRec = null;
            if(apiResponse != null) {
                bookRec = apiResponse.results().getFirst();
            }
            assert bookRec != null;
            this.book = new Book(bookRec);
            this.writer = new Writer(bookRec.authors().getFirst());

            this.book.addWriterList(writer);
            this.writer.addBookList(book);

            this.bookService.saveBook(this.book);

            this.showBookData(this.book);


        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar o json: " + e);
        }
    }

    private void showWriterData(Writer writer){
        System.out.println("Name..........:     "+writer.getName());
        System.out.println("Ano/Nascimento:     "+writer.getBirthYear());
        System.out.println("Ano/Morte.....:     "+writer.getDeathYear());
        System.out.println("Livros........:     "+writer.getBooksList().stream().map(Book::getTitle).toList());
        System.out.println();
    }

    private void showBookData(Book book) {
        System.out.println("Titulo........:     " + book.getTitle());
        System.out.println("Idioma........:     " + book.getLanguage());
        System.out.println("Escritores....:     [");
        book.getWritersList().forEach(w -> System.out.println(w.getName()));
        System.out.println("]");
        System.out.println("Total Download:     " + book.getSelledCopies());
        System.out.println();

    }

}
