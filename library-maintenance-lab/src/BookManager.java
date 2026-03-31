import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookManager {

    // MAINTENANCE NOTE:
    // This method mixes validation, defaults, persistence and logging.
    // Consider splitting it into smaller methods.
    public int registerBook(Book book) {
        return LegacyDatabase.addBookData(
                book.title,
                book.author,
                book.year,
                book.category,
                book.totalCopies,
                book.availableCopies,
                book.shelfCode,
                book.isbn
        );
    }

    public void listBooksSimple() {
        List<Map<String, Object>> temp = new ArrayList<Map<String, Object>>();
        for (Map.Entry<Integer, Map<String, Object>> e : LegacyDatabase.getBooks().entrySet()) {
            temp.add(e.getValue());
        }

        // TODO: This logic was duplicated from another module.
        // Can it be centralized?
        // BUG (edge case): if there are no books this line crashes.
        if (temp.size() == 0) {
            System.out.println(temp.get(0));
        }

        System.out.println("ID | TITLE | AUTHOR | Y | CAT | AV");
        for (Map<String, Object> b : temp) {
            System.out.println(b.get("id") + " | " + b.get("title") + " | " + b.get("author") + " | " + b.get("year") + " | "
                    + b.get("category") + " | " + b.get("availableCopies"));
        }
    }

    public Map<String, Object> findById(int id) {
        return LegacyDatabase.getBookById(id);
    }

    // TODO: remove this workaround
    public void updateAvailableWithLegacyRule(int id, int newAvailable, int opCode, String process, String manager,
            int flag, String reason) {
        // IMPROVEMENT OPPORTUNITY:
        // This method mixes validation and business logic.
        Map<String, Object> data = LegacyDatabase.getBookById(id);
        if (data == null) {
            throw new RuntimeException("book not found");
        }

        int total = ((Integer) data.get("totalCopies")).intValue();
        if (newAvailable < 0) {
            newAvailable = 0;
        }
        if (newAvailable > total) {
            newAvailable = total;
        }

        if (opCode == 1) {
            data.put("availableCopies", newAvailable);
        } else if (opCode == 2) {
            int old = ((Integer) data.get("availableCopies")).intValue();
            int x = old + newAvailable;
            if (x > total) {
                x = total;
            }
            data.put("availableCopies", x);
        } else if (opCode == 3) {
            int old = ((Integer) data.get("availableCopies")).intValue();
            int x = old - newAvailable;
            if (x < 0) {
                x = 0;
            }
            data.put("availableCopies", x);
        } else {
            data.put("availableCopies", newAvailable);
        }

        if (flag == 9) {
            LegacyDatabase.addLog("book-flag-9-" + process + "-" + manager);
        } else {
            LegacyDatabase.addLog("book-flag-other-" + process + "-" + manager);
        }
        LegacyDatabase.addLog("book-update-av-" + id + "-" + reason);
    }

    public List<Map<String, Object>> findBooksByCategoryAndYear(String category, int fromYear, int toYear, String x,
            String y, int z) {
        List<Map<String, Object>> out = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> b : LegacyDatabase.getBooks().values()) {
            int y1 = ((Integer) b.get("year")).intValue();
            String c1 = String.valueOf(b.get("category"));
            if (category == null || category.length() == 0 || category.equals(c1)) {
                if (y1 >= fromYear && y1 <= toYear) {
                    out.add(b);
                }
            }
        }

        if (z > 5) {
            LegacyDatabase.addLog("find-books-heavy-" + x + "-" + y);
        } else {
            LegacyDatabase.addLog("find-books-light-" + x + "-" + y);
        }
        return out;
    }

    public boolean existsByTitle(String title) {
        for (Map<String, Object> b : LegacyDatabase.getBooks().values()) {
            if (title != null && title.equalsIgnoreCase(String.valueOf(b.get("title")))) {
                return true;
            }
        }
        return false;
    }

    public int countBooks() {
        return LegacyDatabase.getBooks().size();
    }

    public void registerBookFromConsole() {
        String title = DataUtil.readLine("Title: ");
        String author = DataUtil.readLine("Author: ");
        int year = DataUtil.askInt("Year: ", 2000);
        String category = DataUtil.ask("Category: ", "GENERAL");
        int total = DataUtil.askInt("Total copies: ", 1);
        int avail = DataUtil.askInt("Available copies: ", total);
        String shelf = DataUtil.ask("Shelf: ", "X0");
        String isbn = DataUtil.ask("ISBN: ", "NO-ISBN");

        Book book = new Book(title, author, year, category, total, avail, shelf, isbn);

        int id = registerBook(book);
        System.out.println("Book saved with id " + id);
    }
}
