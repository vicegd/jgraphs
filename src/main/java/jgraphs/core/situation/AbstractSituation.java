package jgraphs.core.situation;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractSituation implements ISituation {
    protected int n;
    protected int level;
	protected List<ISituation> situations;
	
    public AbstractSituation(int n) {
    	this.n = n;
    	this.level = 0;
    	this.situations = new ArrayList<ISituation>();
    }
    
	@Override
	public boolean hasFinished() {
		if (this.checkStatus() != -1) return true;
		return false;
	}
	
	@Override
	public List<ISituation> nextSituations() {
		return this.nextSituations(-1, null);
	}
	
    @Override
    public String toString() {
    	var sb = new StringBuilder();
    	sb.append("Situation:\n");
    	sb.append("\tSize: \t\t" + this.n + "\n");
    	sb.append("\tLevel: \t\t" + this.level + "\n");
    	sb.append("\tStatus: \t" + this.checkStatus() + "\n");
        sb.append("\tValues: \t" + this.getValuesToString());
        return sb.toString();
    }
    
    public int getSize() {
    	return this.n;
    }
    
    public void setSize(int n) {
    	this.n = n;
    }
    
    public int getLevel() {
    	return this.level;
    }
    
    public void setLevel(int level) {
    	this.level = level;
    }
    
    protected void copyInfo(AbstractSituation source, AbstractSituation target) {
    	target.n = source.n;
    	target.level = source.level;
    }
    
	@Override
	public boolean equals(Object obj) {
		var that = (AbstractSituation)obj;

		if (this.n != that.n) return false;
		if (this.level != that.level) return false;
		if (this.checkStatus() != that.checkStatus()) return false;
		if (!this.getValuesToString().equals(that.getValuesToString())) return false;
		
        return true;
	}

}
