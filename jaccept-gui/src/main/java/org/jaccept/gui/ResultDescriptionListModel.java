package org.jaccept.gui;

import java.util.List;

import javax.swing.DefaultListModel;

import org.jaccept.TestEventListener;
import org.jaccept.TestEventManager;
import org.testng.ISuite;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;


public class ResultDescriptionListModel extends DefaultListModel implements TestEventListener {
    private int aResultCounter = 0;

    public ResultDescriptionListModel() {
        super();
        TestEventManager.addTestListener(this);
    }

    public void caseStarted(String name) {
        aResultCounter = 0;
        this.clear();
    }

    public void stepStarted(String stimuli, String expectedResult) {  
        if (this.getSize() == 3) removeElementAt(0);
        aResultCounter++;
        addElement(aResultCounter+"  "+expectedResult);
    }

    public void stepEnded() {}

    public void description(String description) {}

    public void reference(String reference) {}

    public void projectStarted(String name) {}

    public void addStimuli(String stimuli) {}

    public void addResult(String result) {}

	public void onFinish(ITestContext arg0) {
	}

	public void onStart(ITestContext arg0) {
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
	}

	public void onTestFailure(ITestResult arg0) {
	}

	public void onTestSkipped(ITestResult arg0) {
	}

	public void onTestStart(ITestResult arg0) {
        aResultCounter = 0;
        this.clear();
	}

	public void onTestSuccess(ITestResult arg0) {
	}

	public void onFinish(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onStart(ISuite arg0) {
		// TODO Auto-generated method stub
		
	}

	public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1,
			String arg2) {
		// TODO Auto-generated method stub
		
	}
}
