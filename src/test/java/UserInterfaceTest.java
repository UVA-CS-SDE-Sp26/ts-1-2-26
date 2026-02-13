import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void testListrequest(){
        ProgramControl mockPC = mock(ProgramControl.class);
        UserInterface ui = new UserInterface(mockPC);

        String result = ui.requestHandling();
        assertTrue(result.contains(""));
    }
}