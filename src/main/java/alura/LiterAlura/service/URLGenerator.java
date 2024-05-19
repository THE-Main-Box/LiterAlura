package alura.LiterAlura.service;

public class URLGenerator {
    private final String SEARCH_ADRESS = "https://gutendex.com/books/?search=";

    public String generateSearchUrlString(String title) {

        return SEARCH_ADRESS + title.replaceAll(" ", "+");

    }
}
