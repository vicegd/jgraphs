package jgraphs.core.situation;

import java.util.List;

public class IntArraySituation extends AbstractSituation {
	protected int[] values;

    public IntArraySituation(int n) {
    	super(n);
    	this.values = new int[n];
    }
    
    @Override
    public String getValuesToString() {
        var v = new StringBuilder();
        for (var i = 0; i < values.length; i++) {
            v.append(values[i] + " ");
        } 
        return v.toString();
    }
    
    @Override
    public String getValuesToHTML() {
        var v = new StringBuilder();
        v.append("<p>");
        for (var i = 0; i < values.length; i++) {
            v.append(values[i] + " ");
        } 
        v.append("</p>");
        return v.toString();
    }
       
    public int[] getValues() {
    	return this.values;
    }
    
    public void setValues(int[] values) {
    	this.values = values;
    }
    
    protected void copyInfo(IntArraySituation source, IntArraySituation target) {
    	super.copyInfo(source, target);
    	target.values = source.values.clone();
    }

	@Override
	public ISituation createNewSituation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ISituation> nextSituations(int participant, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int checkStatus() {
		// TODO Auto-generated method stub
		return 0;
	}
 
}

