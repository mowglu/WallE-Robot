public class GrabObject implements Action{

    /*
    3. Grab an object: extend the arm, close the gripper, retract the arm
     */

    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * If it return true, then the action has been executed. If it is false, the action has not been executed.
     * @param pRobot the robot for which action is executed on.
     */
    @Override
    public boolean executeAction(Robot pRobot) {
        // Need to ensure that the arm is retracted for the extend() and the grabber is open for the closeGripper()

        // If the gripper is already holding an object, then opening it would be the characteristic of release action!
        // Hence, this is the first condition to check and break if it's true
        if (pRobot.getGripperState() == Robot.GripperState.HOLDING_OBJECT){
            return false;
        }

        // First ensure that the arm is retracted!
        if (pRobot.getArmState() != Robot.ArmState.RETRACTED) {
            pRobot.retractArm();
        }

        // Then ensure gripper is open!
        if (pRobot.getGripperState() != Robot.GripperState.OPEN){
            pRobot.openGripper();
        }

        //Now can execute the grabbing action sequence!
        pRobot.extendArm();
        pRobot.closeGripper();
        pRobot.retractArm();
        return true;
    }

    /**
     * This method is an implementation of the Visitor design pattern, where for all the concrete actions, this method
     * is overridden and implemented.
     *
     * @param pVisitor the visitor of the concrete visitor classes, like ComputationDistance and ComputationCompactor
     */
    @Override
    public void acceptVisitor(Visitor pVisitor) {
        pVisitor.visitGrabObject(this);
    }

    @Override
    public String toString(){
        return "Grab";
    }
}
