public class ComputationCompactor implements Visitor{

    private int compactedItemsComputed = 0;

    public int getCompactedItemsComputed(){
        return compactedItemsComputed;
    }


    @Override
    public void visitGrabObject(GrabObject pGrabObject) {

    }

    @Override
    public void visitEmptyCompactor(EmptyCompactor pEmptyCompactor) {

    }

    @Override
    public void visitMoveRobot(MoveRobot pMoveRobot) {

    }

    @Override
    public void visitReleaseObject(ReleaseObject pReleaseObject) {

    }

    @Override
    public void visitTurnRobot(TurnRobot pTurnRobot) {

    }

    @Override
    public void visitCompactObject(CompactObject pCompactObject) {
        compactedItemsComputed ++;
    }

    @Override
    public void visitForceRecharge(RechargeDecorator pRechargeDecorator) {

    }
}
