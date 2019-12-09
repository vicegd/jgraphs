package jgraphs.core.situation;

public abstract class AbstractIntTableSituation extends AbstractSituation {
	protected int[][] values;

    public AbstractIntTableSituation(int n) {
    	super(n);
    	this.values = new int[n][n];
    }
    
    @Override
    public String getValuesToString() {
    	var v = new StringBuilder();
        for (var i = 0; i < this.values.length; i++) {
            v.append("\n");
        	for (int j = 0; j < this.values.length; j++) {
            	v.append(this.values[i][j] + " ");
            }
        }
        return v.toString();
    }
    
    @Override
    public String getValuesToHTML() {
        var v = new StringBuilder();
        for (var i = 0; i < this.values.length; i++) {
            v.append("|");
        	for (var j = 0; j < this.values.length; j++) {
        		v.append(this.values[i][j] + "|");
        	}
        	v.append("<br/>");
        } 
        return v.toString();
    }
           
    protected void copyInfo(AbstractIntTableSituation source, AbstractIntTableSituation target) {
    	super.copyInfo(source, target);
    	target.values = new int[target.n][target.n];
        for (var i = 0; i < super.n; i++) {
            for (var j = 0; j < super.n; j++) {
                target.values[i][j] = source.values[i][j];
            }
        }
    }
  
}