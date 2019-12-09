package jgraphs.core.situation;

public abstract class AbstractIntArraySituation extends AbstractSituation {
	protected int[] values;

    public AbstractIntArraySituation(int n) {
    	super(n);
    	this.values = new int[n];
    }
    
    @Override
    public String getValuesToString() {
        var v = new StringBuilder();
        for (var i = 0; i < values.length; i++) {
            v.append(values[i]+1 + " ");
        } 
        return v.toString();
    }
    
    @Override
    public String getValuesToHTML() {
        var v = new StringBuilder();
        v.append("<p>");
        for (var i = 0; i < values.length; i++) {
            v.append(values[i]+1 + " ");
        } 
        v.append("</p>");
        return v.toString();
    }
    
    protected void copyInfo(AbstractIntArraySituation source, AbstractIntArraySituation target) {
    	super.copyInfo(source, target);
    	target.values = source.values.clone();
    }
  
}

