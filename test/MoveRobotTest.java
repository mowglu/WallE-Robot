import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoveRobotTest {
    WallE robot;
    MoveRobot actionToTest;
    VisitorStub visitorStub;
    ExtendStub extendStub;

    static class ExtendStub {
        public void executeAction(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All this must do is extend the arm

            // Reflection to force change the private attribute of arm state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"armState", Robot.ArmState.EXTENDED);
        }
    }

    static class VisitorStub extends ComputationDistance{
        private double totalDistance = 0;

        @Override
        public double getMoveRobotComputed(){
            return totalDistance;
        }

        @Override
        public void visitMoveRobot(MoveRobot moveRobot){
            totalDistance+=moveRobot.getDistance();
        }
    }

    @BeforeEach
    void setUp(){
        //Arrange
        robot = new WallE();
        actionToTest = new MoveRobot(100);
        extendStub = new ExtendStub();
    }

    @Test
    void testMoveRobot() throws NoSuchFieldException, IllegalAccessException {
        // Testing whether the constructor properly updates the attribute aDistance
        //Whitebox
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(actionToTest,"aDistance");
        assertEquals("aDistance : 100.0",check);
    }

    @Test
    void testGetDistance() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(100.0, actionToTest.getDistance());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(actionToTest,"aDistance");
        assertEquals("aDistance : 100.0",check);
    }

    @Test
    void testDefaultMoveRobot() throws NoSuchFieldException, IllegalAccessException {
        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check);
    }


    @Test
    void testValidExecuteAction1() throws NoSuchFieldException, IllegalAccessException {


        //Act
        actionToTest.executeAction(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check);
    }

    @Test
    void testValidExecuteAction2() throws NoSuchFieldException, IllegalAccessException {

        //If the arm is not retracted, it retracts it, and then can move validly

        //First extend
        extendStub.executeAction(robot);

        //Act
        actionToTest.executeAction(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check);
    }

    @Test
    void testInvalidExecuteAction() {
        //Act
        try{
            MoveRobot actionToTestFail = new MoveRobot(-1.5);
            actionToTestFail.executeAction(robot);
            fail("This should not be reached. Invalid action executed because the input distance is negative");
        } catch (Error e){
            // An assertion error has indeed occurred if compacted items is 10.
            assertTrue (e instanceof AssertionFailedError);
        }
    }


    @Test
    void testToString(){
        assertEquals( "Move", actionToTest.toString());
    }


    @Test
    void testAcceptVisitor() {
        visitorStub = new VisitorStub();
        actionToTest.acceptVisitor(visitorStub);
        assertEquals(100,visitorStub.getMoveRobotComputed());
    }
}
