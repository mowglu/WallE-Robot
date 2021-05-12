public interface Action {
    /**
     * The abstraction of an action representing an aspect of the Composite design pattern.
     * If it return true, then the action has been executed. If it is false, the action has not been executed.
     * @param pRobot is the Robot to which the Action is applied to. Actions can be applied to different robots, so robot must be
     *               specified via this parameter
     */
    public boolean executeAction(Robot pRobot);

    /**
     * This method is an implementation of the Visitor design pattern, where for all the concrete actions, this method
     * is overridden and implemented.
     * @param pVisitor the visitor of the concrete visitor classes, like ComputationDistance and ComputationCompactor
     */
    public void acceptVisitor(Visitor pVisitor);

    public String toString();
}
