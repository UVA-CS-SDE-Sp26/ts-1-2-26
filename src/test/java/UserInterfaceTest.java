import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
class UserInterfaceTest{
    @Test
    public void testHelpGuide(){
        ProgramControl mockPC = mock(ProgramControl.class);
        UserInterface ui = new UserInterface(mockPC); // Inject the mock

        String result = ui.requestHandling(new String[]{"-h"});
        assertTrue(result.contains("--- File Portal Help ---"));
    }

    @Test
    public void testListRequest() {
        ProgramControl mockPC = mock(ProgramControl.class);
        UserInterface ui = new UserInterface(mockPC);

        when(mockPC.displayFileList()).thenReturn("01 file1.txt");

        String result = ui.requestHandling(new String[]{});

        assertEquals("01 file1.txt", result);
        verify(mockPC).displayFileList();
    }

    @Test
    public void testInvalidFileNumber() {
        ProgramControl mockPC = mock(ProgramControl.class);
        UserInterface ui = new UserInterface(mockPC);

        String result = ui.requestHandling(new String[]{"cipher"});

        assertEquals("Not a valid file number", result);
    }

    @Test
    public void testTooManyArguments() {
        ProgramControl mockPC = mock(ProgramControl.class);
        UserInterface ui = new UserInterface(mockPC);

        String result = ui.requestHandling(new String[]{"1", "key.txt", "N/A"});

        assertEquals("Too many arguments", result);
    }
}