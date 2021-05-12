public class TurnRobot implements Action{

    /*
    2. Turn 90 degrees to the right or left: ensure the arm is retracted, then turn clockwise or anti-clockwise
     */

    private final Rotation aRotation;

    public TurnRobot(Rotation pChoice){
        aRotation = pChoice;
    }

    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * If it return true, then the action has been executed. If it is false, the action has not been executed.
     */
    @Override
    public boolean executeAction(Robot pRobot) {

        // First ensure that the arm is retracted!
        if (pRobot.getArmState() != Robot.ArmState.RETRACTED) {
            pRobot.retractArm();
        }

        // Now that arm is retracted, it can rotate

        // Rotating right means rotating clockwise -> positive 90 degrees.
        // Rotating left means rotating counter clockwise -> negative 90 degrees.
        if (aRotation == Rotation.RIGHT){
            pRobot.turnRobot(90);
        }
        else{
            pRobot.turnRobot(-90);
        }

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
        pVisitor.visitTurnRobot(this);
    }

    @Override
    public String toString(){
        return "Turn";
    }
}
