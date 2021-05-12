public interface Visitor {

    public void visitGrabObject(GrabObject pGrabObject);
    public void visitEmptyCompactor(EmptyCompactor pEmptyCompactor);
    public void visitMoveRobot(MoveRobot pMoveRobot);
    public void visitReleaseObject(ReleaseObject pReleaseObject);
    public void visitTurnRobot(TurnRobot pTurnRobot);
    public void visitCompactObject(CompactObject pCompactObject);
    public void visitForceRecharge(RechargeDecorator pRechargeDecorator);


}
