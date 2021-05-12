import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComputationCompactorTest {
    @Test
    void testGetCompactedItemsComputed(){
        //Arrange
        int result;
        ComputationCompactor computationCompactor = new ComputationCompactor();
        CompactObject compactObject = new CompactObject();
        //Accepting the visitor twice!
        compactObject.acceptVisitor(computationCompactor);
        compactObject.acceptVisitor(computationCompactor);

        //Act
        result = computationCompactor.getCompactedItemsComputed();

        //Assert
        assertEquals(2,result);
    }
}
