package jgraphs.core.situation;

import java.util.List;

public abstract class AbstractSituation implements ISituation {
    protected int n;
    protected int level;
	
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
    	sb.append("\tLevel: \t\t" + this.level + "\n");
    	sb.append("\tStatus: \t" + this.checkStatus() + "\n");
        sb.append("\tValues: \t" + this.getValuesToString());
        return sb.toString();
    }

}
