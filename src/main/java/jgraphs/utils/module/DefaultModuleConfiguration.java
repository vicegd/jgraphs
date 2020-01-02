package jgraphs.utils.module;

import com.google.inject.AbstractModule;

import jgraphs.core.node.INode;
import jgraphs.core.node.Node;
import jgraphs.core.node.PNode;
import jgraphs.core.state.IState;
import jgraphs.core.state.PState;
import jgraphs.core.state.State;
import jgraphs.core.structure.graph.Graph;
import jgraphs.core.structure.graph.IGraph;
import jgraphs.core.structure.graph.PGraph;
import jgraphs.core.structure.graph.SilentGraph;
import jgraphs.core.structure.graph.SilentPGraph;
import jgraphs.core.structure.tree.ITree;
import jgraphs.core.structure.tree.PTree;
import jgraphs.core.structure.tree.SilentPTree;
import jgraphs.core.structure.tree.SilentTree;
import jgraphs.core.structure.tree.Tree;

public class DefaultModuleConfiguration extends AbstractModule {
	protected EModuleConfiguration moduleConfiguration;
	
	public DefaultModuleConfiguration(EModuleConfiguration moduleConfiguration) {
		this.moduleConfiguration = moduleConfiguration;
	}
	
    @Override
    protected void configure() {
    	switch (this.moduleConfiguration) {
    	case BASIC:
    		this.basic();
    		break;
    	case PBASIC:
    		this.pBasic();
    		break;
    	case SILENTBASIC:
    		this.silentBasic();
    		break;
    	case SILENTPBASIC:
    		this.silentPBasic();
    		break;
    	}
    }
	
    protected void basic() {
    	bind(ITree.class).to(Tree.class);
    	bind(IGraph.class).to(Graph.class);
    	bind(INode.class).to(Node.class);
    	bind(IState.class).to(State.class);
    }
    
    protected void pBasic() {
    	bind(ITree.class).to(PTree.class);
    	bind(IGraph.class).to(PGraph.class);
    	bind(INode.class).to(PNode.class);
    	bind(IState.class).to(PState.class);
    }
    
    protected void silentBasic() {
    	bind(ITree.class).to(SilentTree.class);
    	bind(IGraph.class).to(SilentGraph.class);
    	bind(INode.class).to(Node.class);
    	bind(IState.class).to(State.class);
    }
    
    protected void silentPBasic() {
    	bind(ITree.class).to(SilentPTree.class);
    	bind(IGraph.class).to(SilentPGraph.class);
    	bind(INode.class).to(PNode.class);
    	bind(IState.class).to(PState.class);
    }
}