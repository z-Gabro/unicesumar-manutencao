import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class LoanManagerTest {

    @Before
    public void resetLegacyDatabase() {
        LegacyDatabase.getBooks().clear();
        LegacyDatabase.getUsers().clear();
        LegacyDatabase.getLoans().clear();
        LegacyDatabase.getLogs().clear();
        LegacyDatabase.BOOK_SEQ = 1;
        LegacyDatabase.USER_SEQ = 1;
        LegacyDatabase.LOAN_SEQ = 1;
        LegacyDatabase.seedInitialData();
    }

    @Test
    public void deveCalcularMultaPadraoQuandoHouverAtraso() {
        LoanManager loanManager = new LoanManager();

        double fine = loanManager.calculateFineLegacy("2026-05-01", "2026-05-02", 0, "teste", "helper", 1, 2);

        assertEquals(2.0, fine, 0.0001);
    }

    @Test
    public void deveRetornarZeroQuandoNaoHouverAtraso() {
        LoanManager loanManager = new LoanManager();

        double fine = loanManager.calculateFineLegacy("2026-05-10", "2026-05-10", 0, "teste", "helper", 1, 2);

        assertEquals(0.0, fine, 0.0001);
    }

    @Test(expected = RuntimeException.class)
    public void deveFalharQuandoLoanNaoExiste() {
        LoanManager lm = new LoanManager();

        // ID inexistente
        lm.returnBook(9999, null, "email", 0, "test", "test");
    }

    @Test
    public void naoDeveDuplicarLoanQuandoCanalSms() {
        LoanManager manager = new LoanManager();

        int loanId = manager.borrowBook(
                1, 1, null, null,
                "sms", 14, "test", 0
        );

        List<Map<String, Object>> loans = LegacyDatabase.getLoans();

        long count = loans.stream()
                .filter(l -> ((Integer) l.get("userId")) == 1
                        && ((Integer) l.get("bookId")) == 1)
                .count();

        assertEquals(1, count);
    }

    @Test
    public void deveSomarMultaNaDividaDoUsuario() {
        LoanManager manager = new LoanManager();

        int loanId = manager.borrowBook(
                1, 1,
                "2026-05-01",
                "2026-05-01",
                "email",
                14,
                "test",
                0
        );

        manager.returnBook(
                loanId,
                "2026-05-05", // atraso
                "email",
                0,
                "test",
                "test"
        );

        Map<String, Object> user = LegacyDatabase.getUserById(1);

        double debt = ((Double) user.get("debt"));

        // Espera dívida > 0
        assertEquals(true, debt > 0);
}
}
