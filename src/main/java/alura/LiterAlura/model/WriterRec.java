package alura.LiterAlura.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record WriterRec (
        String name,
        Integer birth_year,
        Integer death_year
){
}
