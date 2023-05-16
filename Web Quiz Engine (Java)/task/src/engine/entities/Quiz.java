package engine.entities;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.*;

@Entity
public class Quiz {

    @Id
    @GeneratedValue()
    private long id;

    private String author;
    
    private String title;
    
    private String text;
    
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<String> options;
    @ElementCollection
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Integer> answer;

    public Quiz() {
        this.answer = new TreeSet<>();
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public void setAnswer(Set<Integer> answer) {
        if (answer != null) {
            this.answer = answer;
        }
    }

    public long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public List<String> getOptions() {
        return options;
    }

    public Set<Integer> getAnswer() {
        return answer;
    }
}
