package alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookRec(
        String title,
        List<WriterRec> authors,
        List<String> languages,
        Integer download_count
) {
}
