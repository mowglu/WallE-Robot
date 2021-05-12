public class ComputationDistance implements Visitor{

    private double totalDistance = 0;

    public double getMoveRobotComputed(){
        return totalDistance;
    }

    @Override
    public void visitGrabObject(GrabObject pGrabObject) {

    }

    @Override
    public void visitEmptyCompactor(EmptyCompactor pEmptyCompactor) {

    }

    @Override
    public void visitMoveRobot(MoveRobot pMoveRobot) {
        totalDistance += pMoveRobot.getDistance();
    }

    @Override
    public void visitReleaseObject(ReleaseObject pReleaseObject) {

    }

    @Override
    public void visitTurnRobot(TurnRobot pTurnRobot) {

    }

    @Override
    public void visitCompactObject(CompactObject pCompactObject) {

    }

    @Override
    public void visitForceRecharge(RechargeDecorator pRechargeDecorator) {

    }
}
