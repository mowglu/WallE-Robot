public class MoveRobot implements Action{

    /*
    1. Move forward some distance: ensure the arm is retracted, then move forward
     */

    private final double aDistance;

    public MoveRobot(double pDistance){
        aDistance = pDistance;
    }

    public double getDistance(){
        return aDistance;
    }

    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * If it return true, then the action has been executed. If it is false, the action has not been executed.
     */
    @Override
    public boolean executeAction(Robot pRobot) {

        // The distance should be positive for the moving action to take place. So if distance is negative, nothing happens!
        if (aDistance < 0){
            return false;
        }

        // First ensure that the arm is retracted!
        if (pRobot.getArmState() != Robot.ArmState.RETRACTED) {
            pRobot.retractArm();
        }

        // Now, the robot is retracted and so moveRobot can be called!
        pRobot.moveRobot(aDistance);
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
        pVisitor.visitMoveRobot(this);
    }

    @Override
    public String toString(){
        return "Move";
    }
}
