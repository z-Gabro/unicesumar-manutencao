public class Book {
    String title;
    String author;
    int year;
    String category;
    int totalCopies;
    int availableCopies;
    String shelfCode;
    String isbn;

    public Book(String title, String author, int year, String category, int totalCopies, int availableCopies, String shelfCode, String isbn) {
        if (title == null || title.isBlank()) {
            throw new RuntimeException("Título Obrigatório!");
        }

        if (author == null || author.isBlank()) {
            throw new RuntimeException("Autor Obrigatório!");
        }

        this.title = title;
        this.author = author;
        this.year = (year < 0) ? 1900 : year;
        this.category = (category == null || category.isBlank()) ? "GENERAL" : category;
        this.totalCopies = (totalCopies <= 0) ? 1 : totalCopies;
        this.availableCopies = (availableCopies < 0) ? this.totalCopies : availableCopies;
        this.shelfCode = (shelfCode == null || shelfCode.isBlank()) ? "X0" : shelfCode;
        this.isbn = (isbn == null || isbn.isBlank()) ? "NO-ISBN" : isbn;
    }
}
