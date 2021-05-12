import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TurnRobotTest {
    WallE robot;
    Action actionToTest1;
    Action actionToTest2;
    ExtendStub extendStub;

    static class ExtendStub {
        public void executeAction(Robot pRobot) throws NoSuchFieldException, IllegalAccessException {
            // All this must do is extend the arm

            // Reflection to force change the private attribute of arm state
            PrivateFieldReflectionAccessor.changeStringFieldValue(pRobot,"armState", Robot.ArmState.EXTENDED);
        }
    }

    @BeforeEach
    void setUp(){
        //Arrange
        robot = new WallE();
        actionToTest1 = new TurnRobot(Rotation.LEFT);
        actionToTest2 = new TurnRobot(Rotation.RIGHT);
        extendStub = new ExtendStub();
    }


    @Test
    void testTurnRobot() throws NoSuchFieldException, IllegalAccessException {
        // Testing whether the constructor properly updates the attribute aRotation


        //Whitebox
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(actionToTest1,"aRotation");
        assertEquals("aRotation : LEFT",check1);

        //Whitebox
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(actionToTest2,"aRotation");
        assertEquals("aRotation : RIGHT",check2);
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

        //Assuming arm is retracted, then it can turn

        //Act
        actionToTest1.executeAction(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check1);

        //Act
        actionToTest2.executeAction(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check2);
    }

    @Test
    void testValidExecuteAction2() throws NoSuchFieldException, IllegalAccessException {

        //If the arm is not retracted, it retracts it, and then can move validly


        //First extend
        extendStub.executeAction(robot);

        //Act
        actionToTest1.executeAction(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check1 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check1);

        //Act
        actionToTest2.executeAction(robot);

        //Blackbox
        assertEquals(Robot.ArmState.RETRACTED, robot.getArmState());
        //Whitebox -> Reflection
        String check2 = PrivateFieldReflectionAccessor.getStringFieldValue(robot,"armState");
        assertEquals("armState : RETRACTED", check2);
    }

    @Test
    void testToString(){

        assertEquals( "Turn", actionToTest1.toString());
        assertEquals( "Turn", actionToTest2.toString());
    }

}
