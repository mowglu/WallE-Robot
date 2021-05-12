public class Driver {
    public static void main(String args[]) {

        // Defining 1 robot, 1 program for loading many actions and complex actions, and 2 complex actions
        WallE robot = new WallE();
        Program program1 = new Program();
        ComplexAction moveBackward = new ComplexAction();
        ComplexAction grabCompactEmpty = new ComplexAction();

        // Defining 5 actions
        Action action1 = new MoveRobot(10);
        Action action2 = new GrabObject();
        Action action3 = new TurnRobot(Rotation.LEFT);
        Action action4 = new CompactObject();
        Action action5 = new EmptyCompactor();

        //Complex Action Move backward -> turn, turn, move
        moveBackward.addAction(action3);
        moveBackward.addAction(action3);
        moveBackward.addAction(action1);

        //Complex Action Grab Compact Empty will grab and compact 10 objects, and then empty
        // -> grab, compact, grab, compact, grab, compact, grab, compact, grab, compact, grab, compact, grab, compact, grab, compact, grab, compact, grab, compact, empty!
        for (int i = 0; i < 10; i++) {
            grabCompactEmpty.addAction(action2);
            grabCompactEmpty.addAction(action4);
        }
        grabCompactEmpty.addAction(action5);

        //Program 1 -> move, grab, compact, empty, move backwards, and then grabCompactEmpty!
        program1.addAction(action1);
        program1.addAction(action2);
        program1.addAction(action4);
        program1.addAction(action5);
        program1.addAction(moveBackward);
        program1.addAction(grabCompactEmpty);

        // Execute the program for the robot!
        System.out.println("\nExecuting a program loaded with many actions and complex actions");
        program1.executeProgram(robot);

        //What about if there is an action in the program sequence that will not execute?
        //Program 2 -> move, compact, turn. It will break at 2nd action, because you cannot compact with empty space!
        System.out.println("\nChecking how programs with wrong actions break the program");
        Program program2 = new Program();
        program2.addAction(action1);
        program2.addAction(action4);
        program2.addAction(action3);

        program2.executeProgram(robot);

        //Can also check that during a complex command, if there is a break, the rest of the program will not execute
        //Program 3 -> move, {move, compact, turn}, turn. It will break at 2nd action, 2nd item, because you cannot compact with empty space!
        System.out.println("\nChecking how programs with wrong complex actions break the program");
        Program program3 = new Program();
        program3.addAction(action1);
        program3.addAction(program2.programToComplexAction());
        program3.addAction(action3);

        program3.executeProgram(robot);

        //Defining 1 new program, and a recharge function added to the grabCompactEmpty complex action through Decorator pattern!
        System.out.println("\nDefining a new functionality for force recharging a complex action, and then executing program");
        Program program4 = new Program();
        RechargeDecorator rechargeThenGrabCompactEmpty = new RechargeDecorator(grabCompactEmpty);

        program4.addAction(rechargeThenGrabCompactEmpty);
        program4.executeProgram(robot);

        //Defining 1 new program, and a recharge function added to the move basic action through Decorator pattern!
        System.out.println("\nDefining a new functionality for force recharging a basic action, and then executing program");
        Program program5 = new Program();
        RechargeDecorator rechargeThenMove = new RechargeDecorator(action1);

        program5.addAction(action1);
        program5.addAction(action3);
        program5.addAction(rechargeThenMove);

        program5.executeProgram(robot);

        // Creating and editing programs!
        //Program 6 -> move, turn
        System.out.println("\nTesting edit methods (add and remove actions) on a program");
        Program program6 = new Program();
        // Add function of "edit" -> Adds the objects in a list.
        program6.addAction(action1);
        program6.addAction(action3);
        // Remove function of "edit" -> Removes the first object that matches passed argument from list.
        program6.removeAction(action1);
        program6.executeProgram(robot);
        // Note that complex actions implicitly add a sequence of basic actions to make it. Suppose a wrong action is added,
        // then the complex action can make use of the remove() method too.

        // With visitor design pattern, concrete additional functionalities are included to all actions.
        System.out.println("\nChecking the computations for compact!");
        System.out.println(program1.getCompactComputation());
        System.out.println("\nChecking the computations for move!");
        System.out.println(program1.getTotalDistance());

    }
}
