import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ComputationDistanceTest {

    @Test
    void testGetTotalDistance(){
        //Arrange
        double distance;
        ComputationDistance computationDistance = new ComputationDistance();
        MoveRobot moveRobot = new MoveRobot(10);
        //Accepting the visitor 3 times!
        moveRobot.acceptVisitor(computationDistance);
        moveRobot.acceptVisitor(computationDistance);
        moveRobot.acceptVisitor(computationDistance);

        //Act
        distance = computationDistance.getMoveRobotComputed();

        //Assert
        assertEquals(30,distance);

    }
}
