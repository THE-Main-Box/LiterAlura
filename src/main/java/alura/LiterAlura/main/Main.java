package alura.LiterAlura.main;

import alura.LiterAlura.entity.Book;
import alura.LiterAlura.entity.Writer;
import alura.LiterAlura.model.ApiResponse;
import alura.LiterAlura.model.BookRec;
import alura.LiterAlura.service.ConnectToGutendexApi;
import alura.LiterAlura.service.ConvertToRec;
import alura.LiterAlura.service.URLGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Main {

    private Scanner reader = new Scanner(System.in);
    private ConnectToGutendexApi connectToGutendexApi = new ConnectToGutendexApi();
    private ConvertToRec conversor = new ConvertToRec();
    private URLGenerator urlGenerator = new URLGenerator();

    private Writer writer;
    private Book book;

//    private final BookService bookService;
//
//    public Main() {
//        this.bookService = bookService;
//    }


    public void showMenu() {
        int choice = -1;

        while (choice != 0) {
            System.out.println("***********Escolha uma Opção************");
            System.out.println("""
                    1 - Registrar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar em um determinado idioma
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
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
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
//Realiza uma pesquisa web com o nome de um livro e seta em variáveis globais o livro e seus escritores e os relaciona
    private void setBook() {
        System.out.println("Qual o titulo do livro");
        String userResponse = reader.nextLine();

        userResponse = urlGenerator.generateSearchUrlString(userResponse);

        String json = connectToGutendexApi.connect(userResponse);

        try {
            ApiResponse apiResponse = conversor.convert(json, ApiResponse.class);
            BookRec bookRec = apiResponse.results().getFirst();

            this.book = new Book(bookRec);
            this.writer = new Writer(bookRec.authors().getFirst());
            this.book.addWriterList(writer);

            this.showBookData();

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erro ao processar o json: " + e);
        }
    }

    private void showBookData() {
        System.out.println("Titulo........:     " + book.getTitle());
        System.out.println("Idioma........:     " + book.getLanguage());
        System.out.println("Escritores....:     [");
        book.getWritersList().forEach(w -> System.out.println(w.getName()));
        System.out.println("]");
        System.out.println("Total Download:     " + book.getSelledCopies());

    }

}
