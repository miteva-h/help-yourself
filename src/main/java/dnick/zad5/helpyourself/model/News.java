package dnick.zad5.helpyourself.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Data
@Entity
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;

    private String title;

    private String photoUrl;

    private LocalDate date;

    public News(){}

    public News(String url, String title, String photoUrl, LocalDate date) {
        this.url = url;
        this.title = title;
        this.photoUrl = photoUrl;
        this.date = date;
    }
}
