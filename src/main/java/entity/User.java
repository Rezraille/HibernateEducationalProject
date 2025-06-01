package entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter

@Entity
@Table(name = "users")
public class User
{
    @Id
    @Column(name = "id", unique = true, nullable = false, table = "users")
    private Integer id;
    @Column(name = "name",nullable = false, columnDefinition = "varchar(255)",  table = "users")
    private String name;
    @Column(name = "email",nullable = false,columnDefinition = "varchar(255)",  table = "users")
    private String email;
    @Column(name = "age",nullable = false,  table = "users")
    private Integer age;
    @Column(name = "created_at", nullable = false,  table = "users")
    private LocalDateTime createdAt;
    @Transient
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy HH:mm:ss");

    @Override
    public String toString()
    {
        return "User: " +
                "id = " + id +
                "; name = " + name +
                "; email = " + email +
                "; age = " + age +
                "; createdAt = " + createdAt.format(formatter) +
                '.';
    }

    public boolean isEmpty()
    {
        return id == null & name == null & email == null & age == null;
    }
}
