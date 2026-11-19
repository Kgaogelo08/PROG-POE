package quickchat;
import org.junit.Test;
import static org.junit.Assert.*;
import quickchat.Massage;

public class MassageTest {
    @Test public void testMassageLengthSuccess() { assertTrue(Massage.checkMassageLength("Hi")); }
    @Test public void testRecipientSuccess() { assertTrue(Massage.checkRecipientCell("+27834557896")); }
    @Test public void testRecipientFailure() { assertFalse(Massage.checkRecipientCell("27834557896")); }
    @Test public void testMassageHash() {
        assertEquals("00:0:HI TONIGHT", Massage.createMassageHash("0012345678", 0, "Hi Mike, can you join us for dinner tonight"));
    }
    @Test public void testSentMassagesArrayCorrectlyPopulated() {
        Massage.populateTestData();
        assertEquals("Did you get the cake?", Massage.sentMassages.get(0));
        assertEquals("It is dinner time !", Massage.sentMassages.get(1));
    }
    @Test public void testDisplayLongestMassage() {
        Massage.populateTestData();
        assertEquals("Where are you? You are late! I have asked you to be on time.", Massage.displayLongestMassage());
    }
    @Test public void testSearchForMassageID() {
        Massage.populateTestData();
        String id = Massage.sentIDs.get(1);
        String result = Massage.searchByMassageID(id);
        assertTrue(result.contains("08388884567"));
        assertTrue(result.contains("It is dinner time !"));
    }
    @Test public void testSearchAllMassagesForRecipient() {
        Massage.populateTestData();
        String result = Massage.searchByRecipient("+27838884567");
        assertTrue(result.contains("Where are you? You are late! I have asked you to be on time."));
        assertTrue(result.contains("OK, I am leaving without you."));
    }
    @Test public void testDeleteMassageUsingHash() {
        Massage.populateTestData();
        String hash = Massage.storedHashes.get(0);
        String result = Massage.deleteByHash(hash);
        assertTrue(result.contains("successfully deleted"));
    }
    @Test public void testDisplayReport() {
        Massage.populateTestData();
        String report = Massage.displayReport();
        assertTrue(report.contains("Massage Hash:"));
        assertTrue(report.contains("Did you get the cake?"));
    }
}