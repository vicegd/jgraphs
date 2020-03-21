package jgraphs.core.situation;

public abstract class IntArraySituation extends AbstractSituation {
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
	public boolean equals(Object obj) {
		var that = (IntArraySituation)obj;
		if (!super.equals(obj)) return false;
		for (int i=0; i<values.length; i++) {
			if (this.values[i] != that.values[i]) return false;
		}
        return true;        
	}
}

