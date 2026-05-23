import java.io.Serializable;

import java.util.Scanner;

public class doctor extends Lecturer implements Serializable {
    private static final long serialVersionUID = 1L;

    java.util.HashSet<String> articles = new java.util.HashSet<>();
    public doctor(String Name, int Id, Degree_type degree_type, String Degree_name, int Salary) {
        super(Name, Id, degree_type, Degree_name, Salary);
    }
    public java.util.HashSet<String> getArticles() {
        return articles;
    }
    public void AddArticle(String article) {
        articles.add(article);

    }
    public int get_Number_of_articles() {
        return articles.size();
    }

}
