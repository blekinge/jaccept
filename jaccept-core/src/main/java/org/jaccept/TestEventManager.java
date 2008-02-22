package org.jaccept;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

public class TestEventManager {
    private static final List listerners = new LinkedList();
    private static final TestEventManager instance = new TestEventManager();   

    private final Logger log = Logger.getLogger(TestEventManager.class);

    private final static TestState testState = new TestState();
    
    private TestEventManager(){}

    public static void addTestListener(TestEventListener listener) {
        listerners.add(listener);
    }

    public static void removeTestListener(TestEventListener listener){
        listerners.remove(listener);
    }
    
    public void addProject(String name){
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.projectStarted(name);
        }
    }

    public void addSuite(String name) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.suiteStarted(name);
        }
    }

    public void addCase(String name) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.caseStarted(name);
        }
    }

    public void addTest(String name) {
        getTestState().setStepCounter(0);
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.testStarted(name);
        }
    }
    
    public void addTestEnded() {
        addStepEnd();
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.testEnded();
        }
    }

    public void addStep(String stimuli, String expectedResult) {
        if (getTestState().getStepCounter() > 0) addStepEnd();
        getTestState().setStepCounter(getTestState().getStepCounter()+1);
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.stepStarted(stimuli, expectedResult);
        }
    }
    
    public void addStepEnd() {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.stepEnded();
        }
    }
    
    public void addDescription(String description) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.description(description);
        }
    }
    
    public void addReference(String reference) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.reference(reference);
        }
    }
    
    public void addFailure(String message) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            try {
            	testListener.testFailed(message);
            } catch (Exception e) {
            	log.error("Listerner callback error", e);
            }
        }
    }

    public void addError(String message) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.testError(message);
        }
    }
    
    public void addStimuli(String stimuli) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.addStimuli(stimuli);
        }
    }
    
    public void addResult(String result) {
        Iterator listenerIterator = listerners.iterator();
        while (listenerIterator.hasNext()) {
            TestEventListener testListener = (TestEventListener) listenerIterator.next();
            testListener.addResult(result);
        }
    }
    public static TestEventManager getInstance() {
        return instance ;
    }
    
    public static void block() {
        if (getTestState().getBlocking()) {
            synchronized(TestEventManager.class) {
                try {
                    TestEventManager.class.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace(); 
                }
            }
        }
    }

    public static void unblock() {
        synchronized(TestEventManager.class) {
            TestEventManager.class.notifyAll();
        }
    }

	public static TestState getTestState() {
		return testState;
	}	
}
